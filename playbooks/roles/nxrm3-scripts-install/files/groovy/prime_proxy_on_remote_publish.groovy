@Grab('org.codehaus.groovy.modules.http-builder:http-builder:0.7')
@Grab('oauth.signpost:signpost-core:1.2.1.2')
@Grab('oauth.signpost:signpost-commonshttp4:1.2.1.2')

//import groovyx.net.http.RESTClient
import groovy.json.JsonSlurper

// Test payload use 'args' for webhook payload
//webhookPayloadText = '{"timestamp":"2019-01-04T21:04:17.506+0000","nodeId":"04D27DDC-1115CECC-65E7CC7C-C6362973-5F59C5FE","initiator":"admin/127.0.0.1","repositoryName":"boston-hosted","action":"CREATED","component":{"id":"21031dfaf45e5b5836154baa0dc21f0c","format":"maven2","name":"webgoat","group":"cupitt.com","version":"1.0.2"}}'

def BUFFER_SIZE = 4096
//def payload = new JsonSlurper().parseText(webhookPayloadText)
def payload = new JsonSlurper().parseText(args)

// Only support created for now
if (payload.action != "CREATED") {
  return
}
/*
    Define the proxy and remote repositories here.
    TODO: get the host, port and authentication from somewhere else?
 */
 def query

 switch(payload.component.format) {
   case ~/npm/:
      log.info("Processing NPM Upload")
      query = [
            repository: payload.repositoryName,
            name: payload.component.name,
            version: payload.component.version
      ] 
      break
    case ~/maven2/:
      log.info("Processing maven2 Upload")
      query = [
        repository: payload.repositoryName,
        group: payload.component.group,
        name: payload.component.name,
        version: payload.component.version
      ]
      break
    default:
      return
 }

def queryString = query.collect { it }.join('&')
log.info(queryString)

// Do we have to wait for the component to finish processing?
sleep(5000)

//def nexusRemote = new URL( "http://localhost:8088/service/rest/v1/search?q=" + queryString)
def nexusRemote = new URL( "http://localhost:8081/service/rest/v1/search?q=" + queryString)
        .openConnection() as HttpURLConnection
// nexusRemote.setRequestProperty( 'Authorization', 'Basic YWRtaW46TmV4dXMhMjM=' )
log.info("nexusRemote URL: " + nexusRemote)

if ( nexusRemote.responseCode == 200 ) {
  // get the JSON response
  def json = nexusRemote.inputStream.withCloseable { inStream ->
    new JsonSlurper().parse( inStream as InputStream ) }
  
  log.info("Reply from nexusRemote: " + json)
  log.info("Assets length: " + json.items.assets.size())

  json.items.assets.each {
    def urls = it.downloadUrl
    urls.each {
      urlPath = new URL(it).getPath()
      def nexusProxy = new URL( "http://localhost:8082" + urlPath)
              .openConnection() as HttpURLConnection
      // nexusProxy.setRequestProperty('Authorization', 'Basic YWRtaW46TmV4dXMhMjM=')
      if ( nexusProxy.responseCode == HttpURLConnection.HTTP_OK ) {
        log.info('Priming: ' + urlPath)
        log.info('Content-Type: ' + nexusProxy.getContentType())
        log.info('Content Length: ' + nexusProxy.getContentLength())
        InputStream inputStream = nexusProxy.getInputStream()
        String dev_null = '/dev/null'

        FileOutputStream outputStream = new FileOutputStream(dev_null)

        byte[] buffer = new byte[BUFFER_SIZE]
        while ((bytesRead = inputStream.read(buffer)) != -1) {
          outputStream.write(buffer, 0, bytesRead)
        }

        outputStream.close()
        inputStream.close()
      } else {
        log.error(nexusProxy.responseCode + ": " + nexusProxy.inputStream.text)
      }
    }
  }
} else {
  log.error(nexusRemote.responseCode + ": " + nexusRemote.inputStream.text)
}



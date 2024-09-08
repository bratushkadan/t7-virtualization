const http = require('http');

const handleRequest = function(request, response) {
    console.log(`Получен запрос на URL: ${request.url}`)
    response.writeHead(200)
    response.end("Hello, World!")
}

const server = http.createServer(handleRequest)
server.listen(8080)

const temperatureDAO = require('./temperatureDAO');
const http = require('http');

var serveur = http.createServer(
    async function(requete, reponse)
    {
        if(requete.method === 'GET')
        {
            if(requete.url === '/temperature/liste' || requete.url === '/temperature/liste/')
            {
                const temperature = await temperatureDAO.listerTemperatures();
                reponse.end(JSON.stringify(temperature));
                // JSON.stringify() OWASP DOM based XSS Prevention Cheat Sheet GUIDELINE #10 + https://stackoverflow.com/questions/17785592/difference-between-json-stringify-and-json-parse
            }
            else if(requete.url === '/temperature/moyenne' || requete.url === '/temperature/moyenne/')
            {
                const moyenne = await temperatureDAO.moyenneTemperature();
                reponse.end(JSON.stringify(moyenne));
            }
            else if(requete.url === '/temperature/mediane' || requete.url === '/temperature/mediane/')
            {
                const mediane = await temperatureDAO.medianeTemperature();
                reponse.end(JSON.stringify(mediane));
            }
            else if(requete.url === '/temperature/minimum' || requete.url === '/temperature/minimum/')
            {
                const minimum = await temperatureDAO.minimumTemperature();
                reponse.end(JSON.stringify(minimum));
            }
            else if(requete.url === '/temperature/maximum' || requete.url === '/temperature/maximum/')
            {
                const maximum = await temperatureDAO.maximumTemperature();
                reponse.end(JSON.stringify(maximum));
            }
            else if(requete.url === '/temperature/mode' || requete.url === '/temperature/mode/')
            {
                const mode = await temperatureDAO.modeTemperature();
                reponse.end(JSON.stringify(mode));
            }
        }
    });
serveur.listen(8080);
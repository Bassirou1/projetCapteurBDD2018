var MongoClient = require('mongodb').MongoClient;
var url = "mongodb://capture:capture9182@capture0-shard-00-00-sx85f.mongodb.net:27017,capture0-shard-00-01-sx85f.mongodb.net:27017,capture0-shard-00-02-sx85f.mongodb.net:27017/test?ssl=true&replicaSet=Capture0-shard-0&authSource=admin&retryWrites=true";

exports.listerTemperatures = async function listerTemperatures() {

    return MongoClient.connect(url, {useNewUrlParser: true },function(erreur, database) {
        if (erreur) throw erreur;
        var dbo = database.db("capture");
        dbo.collection("statistiques").find({}).limit(100).toArray(function(erreur, result) {
            if (erreur) throw erreur;
            console.log(result);
            database.close();
        });
    });
};

exports.moyenneTemperature = async function moyenneTemperature(){

    return MongoClient.connect(url,{useNewUrlParser: true }, function(erreur, database) {
        if (erreur) throw erreur;
        var databaseObject = database.db("capture");
        databaseObject.collection("statistiques").find({}, { projection: { _id: 0, timestamp: 1, moyenne: 1 } }).limit(1).toArray(function(erreur, result) {
            if (erreur) throw erreur;
            console.log(result);
            database.close();
        });
    });
};

exports.medianeTemperature = async function medianeTemperature(){

    return MongoClient.connect(url,{useNewUrlParser: true }, function(erreur, database) {
        if (erreur) throw erreur;
        var databaseObject = database.db("capture");
        databaseObject.collection("statistiques").find({}, { projection: { _id: 0, timestamp: 1, mediane: 1 } }).limit(1).toArray(function(erreur, result) {
            if (erreur) throw erreur;
            console.log(result);
            database.close();
        });
    });
};

exports.minimumTemperature = async function minimumTemperature(){

    return MongoClient.connect(url,{useNewUrlParser: true }, function(erreur, database) {
        if (erreur) throw erreur;
        var databaseObject = database.db("capture");
        databaseObject.collection("statistiques").find({}, { projection: { _id: 0, timestamp: 1, minimum: 1 } }).limit(1).toArray(function(erreur, result) {
            if (erreur) throw erreur;
            console.log(result);
            database.close();
        });
    });
};

exports.maximumTemperature = async function maximumTemperature(){

    return MongoClient.connect(url,{useNewUrlParser: true }, function(erreur, database) {
        if (erreur) throw erreur;
        var databaseObject = database.db("capture");
        databaseObject.collection("statistiques").find({}, { projection: { _id: 0, timestamp: 1, maximum: 1 } }).limit(1).toArray(function(erreur, result) {
            if (erreur) throw erreur;
            console.log(result);
            database.close();
        });
    });
};
exports.modeTemperature = async function modeTemperature(){

    return MongoClient.connect(url,{useNewUrlParser: true }, function(erreur, database) {
        if (erreur) throw erreur;
        var databaseObject = database.db("capture");
        databaseObject.collection("statistiques").find({}, { projection: { _id: 0, timestamp: 1, mode: 1 } }).limit(1).toArray(function(erreur, result) {
            if (erreur) throw erreur;
            console.log(result);
            database.close();
        });
    });
};
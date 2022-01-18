const { request, response } = require('express')
const express = require('express') // use express
const app = express()
app.use(express.json())

var con = require('mysql').createConnection({ // setup connection to db
    host: "127.0.0.1",
    port: 3306,
    user: "root",
    password: "abcd"
})

con.connect(function(err){ // connect to db
    if(err) throw err
    console.log("Connected to mysql!")

    var sql = "USE mydb";
    
    con.query(sql, function(err, res){
        if(err) throw err;
        console.log("Database selected : mydb");

        app.post("/enter", (request, response) => { // enter route request handler (to insert data into db)
            console.log("Insert request received")

            sql = "INSERT INTO history VALUES (" +
                request.body.op1 + ", " + request.body.op2 + ", "
                + request.body.res + ", \"" + request.body.operator + "\")";

            con.query(sql, function(err, res){
                if(err) {
                    console.log(err)
                    response.status(406).send()
                } else {
                    console.log("inserted successfully")
                    response.status(202).send()
                }
            })
        })

        app.get("/retrieve", (request, response) => {
            console.log("Retrieve request received")

            sql = "SELECT * FROM history"
            con.query(sql, function(err, res, fields){
                if(err){
                    console.log(err)
                    response.status(500).send()
                } else {
                    console.log(res);
                    response.status(200).send(res)
                }
            })
        })
    })
})

app.listen(3000, () => {
    console.log("Listening on port 3000!")
})
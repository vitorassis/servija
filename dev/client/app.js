const express = require('express')
const app = express()
const port = 3000

app.set('views', __dirname + '/views');
app.engine('html', require('ejs').renderFile);
app.use(express.static('public'));

app.get('/', (req, res) => {
  res.render("index.html")
})
app.get('/login', (req, res) => {
    res.render("login.html")
  })
  app.get('/logout', (req, res) => {
    res.render("logout.html")
  })
  app.get('/cadastro', (req, res) => {
    res.send('Hello World!')
  })
  app.get('/perfil', (req, res) => {
    res.send('Hello World!')
  })

app.listen(port, () => {
  console.log(`Example app listening at http://localhost:${port}`)
})
const express = require('express')
const cors = require('cors')

const app = express()
const PORT = process.env.PORT || 3000
// neu
app.use(cors())

const rezepte = [
  {
    id: 1,
    name: 'Pizza',
    kategorie: 'Italienisch',
    bild: 'pizza.jpg',
    beschreibung: 'Klassisch italienisch.',
    favorit: false
  },
  {
    id: 2,
    name: 'Spaghetti',
    kategorie: 'Pasta',
    bild: 'spaghetti.jpg',
    beschreibung: 'Mit Fleischsauce.',
    favorit: false
  },
  {
    id: 3,
    name: 'Lasagne',
    kategorie: 'Auflauf',
    bild: 'lasagne.jpg',
    beschreibung: 'Mit Béchamelsauce.',
    favorit: false
  }
]

app.get('/rezepte', (req, res) => {
  res.json(rezepte)
})

app.listen(PORT, () => {
  console.log(`✅ Server läuft auf Port ${PORT}`)
})

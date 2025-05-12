const express = require('express')
const cors = require('cors')
require('dotenv').config()
const pool = require('./config/db') // <-- Verbindung zur Render-Datenbank

const app = express()
const PORT = process.env.PORT || 3000

// Middleware
app.use(cors())
app.use(express.json())

// GET /rezepte – holt alle Rezepte aus der DB
app.get('/rezepte', async (req, res) => {
  try {
    const result = await pool.query('SELECT * FROM rezepte')
    const rezepte = result.rows.map(r => ({
      ...r,
      zutaten: r.zutaten ?? [],
      naehrwerte: r.naehrwerte ?? {}
    }))
    res.json(rezepte)
  } catch (err) {
    console.error('❌ Fehler bei GET /rezepte:', err)
    res.status(500).json({ error: 'Fehler beim Abrufen der Rezepte' })
  }
})

// POST /rezepte – speichert ein neues Rezept in der DB
app.post('/rezepte', async (req, res) => {
  const rezept = req.body

  try {
    await pool.query(
        `INSERT INTO rezepte (name, kategorie, bild, beschreibung, favorit, zutaten, naehrwerte)
       VALUES ($1, $2, $3, $4, $5, $6, $7)`,
        [
          rezept.name,
          rezept.kategorie,
          rezept.bild,
          rezept.beschreibung,
          rezept.favorit ?? false,
          JSON.stringify(rezept.zutaten),
          JSON.stringify(rezept.naehrwerte)
        ]
    )
    res.status(201).json({ message: 'Rezept erfolgreich gespeichert' })
  } catch (err) {
    console.error('❌ Fehler bei POST /rezepte:', err)
    res.status(500).json({ error: 'Speichern fehlgeschlagen' })
  }
})

// Server starten
app.listen(PORT, () => {
  console.log(`🚀 Backend läuft auf http://localhost:${PORT}`)
})
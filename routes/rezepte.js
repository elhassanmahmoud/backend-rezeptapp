const express = require('express')
const cors = require('cors')
const bcrypt = require('bcrypt')
const jwt = require('jsonwebtoken')
require('dotenv').config()
const pool = require('./config/db')

const app = express()
const PORT = process.env.PORT || 3000
const SECRET = process.env.JWT_SECRET || 'supergeheim'

// ✅ CORS-Konfiguration
app.use(cors({
    origin: 'https://frontend-rezeptapp.onrender.com',
    methods: ['GET', 'POST'],
    allowedHeaders: ['Content-Type', 'Authorization']
}))

app.use(express.json())

// 🔐 Auth-Middleware
function authMiddleware(req, res, next) {
    const header = req.headers.authorization
    if (!header) return res.status(401).json({ error: 'Kein Token gesendet' })

    const token = header.split(' ')[1]
    try {
        const payload = jwt.verify(token, SECRET)
        req.user = payload
        next()
    } catch (err) {
        return res.status(401).json({ error: 'Ungültiger Token' })
    }
}

// ✅ Registrierung
app.post('/auth/register', async (req, res) => {
    const { email, passwort } = req.body
    try {
        const hashed = await bcrypt.hash(passwort, 10)
        const result = await pool.query(
            'INSERT INTO nutzer (email, passwort) VALUES ($1, $2) RETURNING id, email',
            [email, hashed]
        )
        res.status(201).json(result.rows[0])
    } catch (err) {
        console.error('❌ Fehler bei Registrierung:', err)
        res.status(500).json({ error: 'Registrierung fehlgeschlagen' })
    }
})

// ✅ Login
app.post('/auth/login', async (req, res) => {
    const { email, passwort } = req.body
    try {
        const result = await pool.query('SELECT * FROM nutzer WHERE email = $1', [email])
        const nutzer = result.rows[0]
        if (!nutzer) return res.status(401).json({ error: 'Nutzer nicht gefunden' })

        const ok = await bcrypt.compare(passwort, nutzer.passwort)
        if (!ok) return res.status(401).json({ error: 'Falsches Passwort' })

        const token = jwt.sign({ id: nutzer.id }, SECRET, { expiresIn: '1h' })
        res.json({ token })
    } catch (err) {
        console.error('❌ Fehler beim Login:', err)
        res.status(500).json({ error: 'Login fehlgeschlagen' })
    }
})

// 📥 GET /rezepte – nur eigene
app.get('/rezepte', authMiddleware, async (req, res) => {
    try {
        const result = await pool.query(
            'SELECT * FROM rezepte WHERE nutzer_id = $1',
            [req.user.id]
        )
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

// 📤 POST /rezepte – eigenes Rezept speichern
app.post('/rezepte', authMiddleware, async (req, res) => {
    const rezept = req.body
    const userId = req.user.id

    try {
        const result = await pool.query(
            `INSERT INTO rezepte (name, kategorie, bild, beschreibung, favorit, zutaten, naehrwerte, nutzer_id)
             VALUES ($1, $2, $3, $4, $5, $6, $7, $8)
                 RETURNING *`,
            [
                rezept.name,
                rezept.kategorie,
                rezept.bild,
                rezept.beschreibung,
                rezept.favorit ?? false,
                JSON.stringify(rezept.zutaten),
                JSON.stringify(rezept.naehrwerte),
                userId
            ]
        )
        res.status(201).json(result.rows[0])
    } catch (err) {
        console.error('❌ Fehler bei POST /rezepte:', err)
        res.status(500).json({ error: 'Speichern fehlgeschlagen' })
    }
})

// 🚀 Server starten
app.listen(PORT, () => {
    console.log(`🚀 Backend läuft auf http://localhost:${PORT}`)
})
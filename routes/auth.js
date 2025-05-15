const express = require('express')
const bcrypt = require('bcrypt')
const jwt = require('jsonwebtoken')
const pool = require('../config/db')

const router = express.Router()
const SECRET = process.env.JWT_SECRET || 'supergeheim'

//  Registrierung
router.post('/register', async (req, res) => {
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

//  Login
router.post('/login', async (req, res) => {
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

module.exports = router
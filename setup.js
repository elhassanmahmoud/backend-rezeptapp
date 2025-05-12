require('dotenv').config()
const pool = require('./config/db')

async function setup() {
    try {
        await pool.query(`
      CREATE TABLE IF NOT EXISTS rezepte (
        id SERIAL PRIMARY KEY,
        name TEXT,
        kategorie TEXT,
        bild TEXT,
        beschreibung TEXT,
        favorit BOOLEAN,
        zutaten JSONB,
        naehrwerte JSONB
      );
    `)

        console.log('✅ Tabelle "rezepte" wurde erstellt oder existiert bereits.')
        process.exit()
    } catch (err) {
        console.error('❌ Fehler beim Erstellen der Tabelle:', err)
        process.exit(1)
    }
}

setup()
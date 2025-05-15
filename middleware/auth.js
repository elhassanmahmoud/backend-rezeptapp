const jwt = require('jsonwebtoken')
const SECRET = process.env.JWT_SECRET || 'supergeheim'

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

module.exports = authMiddleware
const express = require('express');
const { Client, LocalAuth } = require('whatsapp-web.js');
const qrcode = require('qrcode-terminal'); // Certifique-se de que esta biblioteca está instalada

const app = express();
const port = 3000;

const client = new Client({
    authStrategy: new LocalAuth(),
});

client.on('qr', (qr) => {
    // Gera o QR Code e exibe no terminal
    qrcode.generate(qr, { small: true });
    console.log('QR Code gerado, escaneie com o WhatsApp.');
});

client.on('ready', () => {
    console.log('Cliente WhatsApp pronto!');
});

// Endpoint para enviar mensagem
app.get('/send', async (req, res) => {
    const number = req.query.number;
    const message = req.query.message;

    try {
        // Enviar mensagem
        await client.sendMessage(`${number}@c.us`, message);
        res.send(`Mensagem enviada para ${number}`);
    } catch (error) {
        console.error(`Erro ao enviar mensagem:`, error);
        res.status(500).send(`Erro ao enviar mensagem: ${error.message}`);
    }
});

client.initialize();
app.listen(port, () => {
    console.log(`Servidor rodando na porta ${port}`);
});

import "dotenv/config";
import express from "express";
import { validateTTSText } from "./validateInput.js";

const app = express();

const port = process.env.PORT || 3001;
const elevenLabsApiKey = process.env.ELEVENLABS_API_KEY;
const elevenLabsVoiceId = process.env.ELEVENLABS_VOICE_ID;

if (!elevenLabsApiKey || !elevenLabsVoiceId) {
    throw new Error("Missing ELEVENLABS_API_KEY or ELEVENLABS_VOICE_ID in .env");
}

app.use(express.json({ limit: "4kb" }));

app.get("/health", (req, res) => {
    res.json({ ok: true });
});

app.post("/tts", async (req, res) => {
   const validation = validateTTSText(req.body?.text);

   if (!validation.isValid) {
        return res.status(400).json({
            error: validation.error,
        });
   }

   try {
       const elevenLabsUrl =
       `https://api.elevenlabs.io/v1/text-to-speech/${elevenLabsVoiceId}` +
        "?output_format=mp3_44100_128";

        const elevenLabsResponse = await fetch(elevenLabsUrl, {
              method: "POST",
              headers: {
                "xi-api-key": elevenLabsApiKey,
                "Content-Type": "application/json",
              },
              body: JSON.stringify({
                text: validation.text,
                model_id: "eleven_multilingual_v2",
              }),
        });

        if (!elevenLabsResponse.ok) {
          const errorText = await elevenLabsResponse.text();

          console.error("ElevenLabs request failed:", {
            status: elevenLabsResponse.status,
            body: errorText,
          });

          return res.status(502).json({
            error: "Text-to-speech generation failed.",
          });
        }

        const audioBuffer = Buffer.from(await elevenLabsResponse.arrayBuffer());

        res.setHeader("Content-Type", "audio/mpeg");
        res.setHeader("Cache-Control", "no-store");
        res.send(audioBuffer);
   } catch (error) {
        console.error("Unexpected TTS proxy error:", error);

        res.status(500).json({
            error: "Unexpected server code.",
        });
   }
});

app.listen(port, () => {
    console.log(`TTS proxy running on http://localhost:${port}`);
})
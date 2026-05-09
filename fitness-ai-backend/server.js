import express from "express";
import cors from "cors";
import "dotenv/config";
import OpenAI from "openai";

const app = express();

app.use(cors());
app.use(express.json());

const client = new OpenAI({
  apiKey: process.env.OPENAI_API_KEY,
});

app.get("/", (req, res) => {
  res.send("Backend is running");
});

app.post("/ai-summary", async (req, res) => {
  try {
    const { sleep, activity, mood, notes } = req.body;

    console.log("Received:", sleep, activity, mood, notes);

    const response = await client.responses.create({
      model: "gpt-4.1-mini",
      input: `
You are a fitness and wellness assistant.

Analyze the user's logs and provide:
- one short insight
- possible pattern
- one improvement suggestion

Keep the response under 3 sentences.
Do not provide medical diagnosis.

User Data:
Sleep: ${sleep}
Activity: ${activity}
Mood: ${mood}

Logs:
${notes}
      `,
    });

    res.json({
      suggestion: response.output_text,
    });

  } catch (error) {
    console.error("AI error:", error);

    res.status(500).json({
      error: "Failed to generate AI insight",
    });
  }
});

app.listen(3000, () => {
  console.log("Server running on http://localhost:3000");
});
import express from "express";
import cors from "cors";
import "dotenv/config";

const app = express();

app.use(cors());
app.use(express.json());

app.get("/", (req, res) => {
  res.send("Backend is running");
});

app.post("/ai-summary", (req, res) => {
  const { sleep, activity, mood, notes } = req.body;

  console.log("Received:", sleep, activity, mood, notes);

  res.json({
    suggestion: "Test works: your backend is connected correctly."
  });
});

app.listen(3000, () => {
  console.log("Server running on http://localhost:3000");
});

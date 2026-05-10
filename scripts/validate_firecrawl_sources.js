const fs = require("fs");
const path = require("path");

const SOURCE_PATH = path.join(__dirname, "firecrawl_sources.json");

function run() {
  if (!fs.existsSync(SOURCE_PATH)) {
    console.error("Missing scripts/firecrawl_sources.json.");
    process.exit(1);
  }

  const data = JSON.parse(fs.readFileSync(SOURCE_PATH, "utf8"));
  const errors = [];

  if (!data || !Array.isArray(data.makers) || data.makers.length === 0) {
    errors.push("makers must be a non-empty array.");
  }

  const makers = data.makers || [];
  makers.forEach((maker, index) => {
    if (!maker.artisanId) errors.push(`makers[${index}].artisanId is required.`);
    if (!maker.artisanName) errors.push(`makers[${index}].artisanName is required.`);
    if (!Array.isArray(maker.sourceUrls) || maker.sourceUrls.length === 0) {
      errors.push(`makers[${index}].sourceUrls must contain at least one URL.`);
    }
    if (!maker.license) errors.push(`makers[${index}].license is required.`);
  });

  if (errors.length) {
    console.error("Validation failed:");
    errors.forEach((err) => console.error(`- ${err}`));
    process.exit(1);
  }

  console.log("firecrawl_sources.json looks valid.");
}

run();


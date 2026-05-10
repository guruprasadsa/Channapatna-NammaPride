const admin = require('firebase-admin');

admin.initializeApp({
  credential: admin.credential.applicationDefault(),
  projectId: 'channapatna-namma-pride-9324b'
});

const db = admin.firestore();

async function deleteCollection(collectionPath) {
  const collectionRef = db.collection(collectionPath);
  const query = collectionRef.orderBy('__name__').limit(100);

  return new Promise((resolve, reject) => {
    deleteQueryBatch(db, query, resolve).catch(reject);
  });
}

async function deleteQueryBatch(db, query, resolve) {
  const snapshot = await query.get();

  const batchSize = snapshot.size;
  if (batchSize === 0) {
    resolve();
    return;
  }

  const batch = db.batch();
  snapshot.docs.forEach((doc) => {
    batch.delete(doc.ref);
  });
  await batch.commit();

  process.nextTick(() => {
    deleteQueryBatch(db, query, resolve);
  });
}

async function seed() {
  try {
    console.log("Cleaning up existing data for a fresh authentic reseed...");
    await deleteCollection('toys');
    await deleteCollection('artisans');
    await deleteCollection('workshops');
    console.log("Cleanup complete.");

    console.log("Starting authentic Firestore seeding with 7 genuine workshops...");

    // 1. Seed Artisans
    const artisansRef = db.collection('artisans');
    const artisans = [
      {
        id: "syed_athar",
        name: "Syed Athar",
        name_en: "Syed Athar",
        name_kn: "ಸೈಯದ್ ಅಥರ್",
        workshopName: "Bharath Art and Crafts",
        workshopName_en: "Bharath Art and Crafts",
        workshopName_kn: "ಭಾರತ್ ಕಲೆ ಮತ್ತು ಕರಕುಶಲ",
        experience: "35 Years",
        bio: "A legendary master turner, Syed carries the legacy of generations. His unit is a primary stop for those seeking the traditional manual lathe-turning experience.",
        bio_en: "A legendary master turner, Syed carries the legacy of generations. His unit is a primary stop for those seeking the traditional manual lathe-turning experience.",
        bio_kn: "ದಂತಕಥೆಯ ಮಾಸ್ಟರ್ ಟರ್ನರ್, ಸೈಯದ್ ತಲೆಮಾರುಗಳ ಪರಂಪರೆಯನ್ನು ಹೊತ್ತಿದ್ದಾರೆ. ಸಾಂಪ್ರದಾಯಿಕ ಹಸ್ತಚಾಲಿತ ಲೇತ್-ಟರ್ನಿಂಗ್ ಅನುಭವವನ್ನು ಬಯಸುವವರಿಗೆ ಇವರ ಘಟಕವು ಪ್ರಮುಖ ನಿಲ್ದಾಣವಾಗಿದೆ.",
        specialties: ["Heritage Toys", "Manual Lathe Work", "Natural Dyes"],
        specialties_en: ["Heritage Toys", "Manual Lathe Work", "Natural Dyes"],
        specialties_kn: ["ಪರಂಪರೆಯ ಗೊಂಬೆಗಳು", "ಹಸ್ತಚಾಲಿತ ಲೇತ್ ಕೆಲಸ", "ನೈಸರ್ಗಿಕ ಬಣ್ಣಗಳು"],
        location: "Nizami Chouk, Channapatna",
        phone: "+91 98765 43210",
        imageUrl: "https://images.unsplash.com/photo-1544005313-94ddf0286df2?q=80&w=1000&auto=format&fit=crop"
      },
      {
        id: "meeran_master",
        name: "Meeran",
        name_en: "Meeran",
        name_kn: "ಮೀರನ್",
        workshopName: "Meeran Art & Crafts",
        workshopName_en: "Meeran Art & Crafts",
        workshopName_kn: "ಮೀರನ್ ಕಲೆ ಮತ್ತು ಕರಕುಶಲ",
        experience: "28 Years",
        bio: "Specializing in export-grade toys, Meeran's precision is unmatched. His nesting dolls are sought after by collectors worldwide.",
        bio_en: "Specializing in export-grade toys, Meeran's precision is unmatched. His nesting dolls are sought after by collectors worldwide.",
        bio_kn: "ರಫ್ತು-ಗುಣಮಟ್ಟದ ಗೊಂಬೆಗಳಲ್ಲಿ ಪರಿಣತಿ ಹೊಂದಿರುವ ಮೀರನ್ ಅವರ ನಿಖರತೆ ಸಾಟಿಯಿಲ್ಲದ್ದು. ಇವರ ನೆಸ್ಟಿಂಗ್ ಗೊಂಬೆಗಳನ್ನು ವಿಶ್ವಾದ್ಯಂತ ಸಂಗ್ರಾಹಕರು ಬಯಸುತ್ತಾರೆ.",
        specialties: ["Nesting Dolls", "Export Quality", "Smooth Finishes"],
        specialties_en: ["Nesting Dolls", "Export Quality", "Smooth Finishes"],
        specialties_kn: ["ನೆಸ್ಟಿಂಗ್ ಗೊಂಬೆಗಳು", "ರಫ್ತು ಗುಣಮಟ್ಟ", "ಸುಗಮ ಫಿನಿಶಿಂಗ್"],
        location: "Jeevanpur Mohalla, Channapatna",
        phone: "+91 87654 32109",
        imageUrl: "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?q=80&w=1000&auto=format&fit=crop"
      },
      {
        id: "noor_salma",
        name: "Noor Salma",
        name_en: "Noor Salma",
        name_kn: "ನೂರ್ ಸಲ್ಮಾ",
        workshopName: "Salma Toys Unit",
        workshopName_en: "Salma Toys Unit",
        workshopName_kn: "ಸಲ್ಮಾ ಗೊಂಬೆಗಳ ಘಟಕ",
        experience: "20 Years",
        bio: "A pioneer for women in the craft, Noor Salma leads a unit dedicated to safe, educational toys for modern nurseries.",
        bio_en: "A pioneer for women in the craft, Noor Salma leads a unit dedicated to safe, educational toys for modern nurseries.",
        bio_kn: "ಕರಕುಶಲತೆಯಲ್ಲಿ ಮಹಿಳೆಯರಿಗೆ ಪ್ರವರ್ತಕಿಯಾಗಿರುವ ನೂರ್ ಸಲ್ಮಾ, ಆಧುನಿಕ ನರ್ಸರಿಗಳಿಗಾಗಿ ಸುರಕ್ಷಿತ, ಶೈಕ್ಷಣಿಕ ಗೊಂಬೆಗಳಿಗಾಗಿ ಮೀಸಲಾದ ಘಟಕವನ್ನು ಮುನ್ನಡೆಸುತ್ತಿದ್ದಾರೆ.",
        specialties: ["Educational Toys", "Infant Safety", "Modern Colors"],
        specialties_en: ["Educational Toys", "Infant Safety", "Modern Colors"],
        specialties_kn: ["ಶೈಕ್ಷಣಿಕ ಗೊಂಬೆಗಳು", "ಶಿಶು ಸುರಕ್ಷತೆ", "ಆಧುನಿಕ ಬಣ್ಣಗಳು"],
        location: "Yarab Nagar, Channapatna",
        phone: "+91 99887 76655",
        imageUrl: "https://images.unsplash.com/photo-1594744803329-a584af1cae24?q=80&w=1000&auto=format&fit=crop"
      },
      {
        id: "shiva_master",
        name: "Shiva",
        name_en: "Shiva",
        name_kn: "ಶಿವ",
        workshopName: "Sri Beereshwara Arts",
        workshopName_en: "Sri Beereshwara Arts",
        workshopName_kn: "ಶ್ರೀ ಬೀರೇಶ್ವರ ಕಲೆ",
        experience: "25 Years",
        bio: "Located in the heart of the Crafts Park, Shiva is known for his large-scale decorative items and traditional lacquered gifts.",
        bio_en: "Located in the heart of the Crafts Park, Shiva is known for his large-scale decorative items and traditional lacquered gifts.",
        bio_kn: "ಕ್ರಾಫ್ಟ್ಸ್ ಪಾರ್ಕ್‌ನ ಹೃದಯಭಾಗದಲ್ಲಿ ನೆಲೆಸಿರುವ ಶಿವ, ತಮ್ಮ ಬೃಹತ್ ಗಾತ್ರದ ಅಲಂಕಾರಿಕ ವಸ್ತುಗಳು ಮತ್ತು ಸಾಂಪ್ರದಾಯಿಕ ಲ್ಯಾಕರ್ ಉಡುಗೊರೆಗಳಿಗೆ ಹೆಸರಾಗಿದ್ದಾರೆ.",
        specialties: ["Home Decor", "Gift Items", "Vibrant Lacquer"],
        specialties_en: ["Home Decor", "Gift Items", "Vibrant Lacquer"],
        specialties_kn: ["ಮನೆ ಅಲಂಕಾರ", "ಉಡುಗೊರೆಗಳು", "ವೈವಿಧ್ಯಮಯ ಲ್ಯಾಕರ್"],
        location: "Crafts Park, Channapatna",
        phone: "+91 94433 22110",
        imageUrl: "https://images.unsplash.com/photo-1513364776144-60967b0f800f?q=80&w=1000&auto=format&fit=crop"
      },
      {
        id: "ajmal_artisan",
        name: "Ajmal",
        name_en: "Ajmal",
        name_kn: "ಅಜ್ಮಲ್",
        workshopName: "Ajmal Handicrafts",
        workshopName_en: "Ajmal Handicrafts",
        workshopName_kn: "ಅಜ್ಮಲ್ ಹ್ಯಾಂಡಿಕ್ರಾಫ್ಟ್ಸ್",
        experience: "22 Years",
        bio: "Ajmal specializes in miniature furniture and traditional dolls that capture the daily life of Karnataka villages.",
        bio_en: "Ajmal specializes in miniature furniture and traditional dolls that capture the daily life of Karnataka villages.",
        bio_kn: "ಅಜ್ಮಲ್ ಕರ್ನಾಟಕದ ಹಳ್ಳಿಗಳ ದೈನಂದಿನ ಜೀವನವನ್ನು ಚಿತ್ರಿಸುವ ಕಿರು ಪೀಠೋಪಕರಣಗಳು ಮತ್ತು ಸಾಂಪ್ರದಾಯಿಕ ಗೊಂಬೆಗಳಲ್ಲಿ ಪರಿಣತಿ ಹೊಂದಿದ್ದಾರೆ.",
        specialties: ["Miniature Furniture", "Village Dolls", "Intricate Carving"],
        specialties_en: ["Miniature Furniture", "Village Dolls", "Intricate Carving"],
        specialties_kn: ["ಕಿರು ಪೀಠೋಪಕರಣಗಳು", "ಹಳ್ಳಿ ಗೊಂಬೆಗಳು", "ಸಂಕೀರ್ಣ ಕೆತ್ತನೆ"],
        location: "Channapatna Crafts Park",
        phone: "+91 91223 34455",
        imageUrl: "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?q=80&w=1000&auto=format&fit=crop"
      },
      {
        id: "parvati_cluster",
        name: "Parvati",
        name_en: "Parvati",
        name_kn: "ಪಾರ್ವತಿ",
        workshopName: "Maya Organic Cluster",
        workshopName_en: "Maya Organic Cluster",
        workshopName_kn: "ಮಾಯಾ ಆರ್ಗ್ಯಾನಿಕ್ ಕ್ಲಸ್ಟರ್",
        experience: "15 Years",
        bio: "Part of the Maya Organic initiative, Parvati focuses on sustainable production and eco-friendly educational kits for kids.",
        bio_en: "Part of the Maya Organic initiative, Parvati focuses on sustainable production and eco-friendly educational kits for kids.",
        bio_kn: "ಮಾಯಾ ಆರ್ಗ್ಯಾನಿಕ್ ಉಪಕ್ರಮದ ಭಾಗವಾಗಿರುವ ಪಾರ್ವತಿ, ಸುಸ್ಥಿರ ಉತ್ಪಾದನೆ ಮತ್ತು ಮಕ್ಕಳಿಗಾಗಿ ಪರಿಸರ ಸ್ನೇಹಿ ಶೈಕ್ಷಣಿಕ ಕಿಟ್‌ಗಳ ಮೇಲೆ ಗಮನ ಹರಿಸುತ್ತಾರೆ.",
        specialties: ["Eco-friendly Kits", "Sustainability", "Childhood Development"],
        specialties_en: ["Eco-friendly Kits", "Sustainability", "Childhood Development"],
        specialties_kn: ["ಪರಿಸರ ಸ್ನೇಹಿ ಕಿಟ್‌ಗಳು", "ಸುಸ್ಥಿರತೆ", "ಬಾಲ್ಯದ ಅಭಿವೃದ್ಧಿ"],
        location: "Kuvempu Nagar, Channapatna",
        phone: "+91 88776 65544",
        imageUrl: "https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?q=80&w=1000&auto=format&fit=crop"
      },
      {
        id: "karthik_vaidyanathan",
        name: "Karthik Vaidyanathan",
        name_en: "Karthik Vaidyanathan",
        name_kn: "ಕಾರ್ತಿಕ್ ವೈದ್ಯನಾಥನ್",
        workshopName: "Varnam Craft Collective",
        workshopName_en: "Varnam Craft Collective",
        workshopName_kn: "ವರ್ಣಂ ಕ್ರಾಫ್ಟ್ ಕಲೆಕ್ಟಿವ್",
        experience: "12 Years",
        bio: "Bridging the gap between traditional craft and modern design, Karthik's work brings Channapatna into contemporary urban homes.",
        bio_en: "Bridging the gap between traditional craft and modern design, Karthik's work brings Channapatna into contemporary urban homes.",
        bio_kn: "ಸಾಂಪ್ರದಾಯಿಕ ಕರಕುಶಲತೆ ಮತ್ತು ಆಧುನಿಕ ವಿನ್ಯಾಸದ ನಡುವಿನ ಅಂತರವನ್ನು ಕಡಿಮೆ ಮಾಡುವ ಕಾರ್ತಿಕ್ ಅವರ ಕೆಲಸವು ಚನ್ನಪಟ್ಟಣವನ್ನು ಸಮಕಾಲೀನ ನಗರ ಮನೆಗಳಿಗೆ ತರುತ್ತದೆ.",
        specialties: ["Contemporary Design", "Modern Home Decor", "Designer Lamps"],
        specialties_en: ["Contemporary Design", "Modern Home Decor", "Designer Lamps"],
        specialties_kn: ["ಸಮಕಾಲೀನ ವಿನ್ಯಾಸ", "ಆಧುನಿಕ ಮನೆ ಅಲಂಕಾರ", "ಡಿಸೈನರ್ ದೀಪಗಳು"],
        location: "Heritage Street, Channapatna",
        phone: "+91 91234 56789",
        imageUrl: "https://images.unsplash.com/photo-1552058544-f2b08422138a?q=80&w=1000&auto=format&fit=crop"
      }
    ];

    for (const artisan of artisans) {
      await artisansRef.doc(artisan.id).set(artisan);
    }
    console.log("Seeded 7 Artisans.");

    // 2. Seed Toys
    const toysRef = db.collection('toys');
    const toys = [
      {
        id: "200001",
        name: "Classic Rocking Horse",
        name_en: "Classic Rocking Horse",
        name_kn: "ಕ್ಲಾಸಿಕ್ ಊಗುಚಾಟ ಕುದುರೆ",
        artisanName: "Syed Athar",
        artisanName_en: "Syed Athar",
        artisanName_kn: "ಸೈಯದ್ ಅಥರ್",
        artisanId: "syed_athar",
        description: "The quintessential Channapatna toy. Hand-turned from Hale wood and dyed with turmeric and indigo.",
        description_en: "The quintessential Channapatna toy. Hand-turned from Hale wood and dyed with turmeric and indigo.",
        description_kn: "ಚನ್ನಪಟ್ಟಣದ ಪ್ರಮುಖ ಗೊಂಬೆ. ಹಾಲೆ ಮರದಿಂದ ಹಸ್ತಚಾಲಿತವಾಗಿ ತಯಾರಿಸಿ ಅರಿಶಿನ ಮತ್ತು ಇಂಡಿಗೋದಿಂದ ಬಣ್ಣಿಸಲಾಗಿದೆ.",
        location: "Nizami Chouk, Channapatna",
        location_en: "Nizami Chouk, Channapatna",
        location_kn: "ನಿಜಾಮಿ ಚೌಕ್, ಚನ್ನಪಟ್ಟಣ",
        giTagNumber: "GI-23-H01",
        imageUrl: "https://images.unsplash.com/photo-1532330393533-443990a51d10?auto=format&fit=crop&q=80&w=800"
      },
      {
        id: "200002",
        name: "Rainbow Stacking Rings",
        name_en: "Rainbow Stacking Rings",
        name_kn: "ಮಳೆಬಿಲ್ಲು ಸ್ಟ್ಯಾಕಿಂಗ್ ರಿಂಗ್ಸ್",
        artisanName: "Noor Salma",
        artisanName_en: "Noor Salma",
        artisanName_kn: "ನೂರ್ ಸಲ್ಮಾ",
        artisanId: "noor_salma",
        description: "Safe, non-toxic educational rings for toddlers. Helps in color recognition and motor skill development.",
        description_en: "Safe, non-toxic educational rings for toddlers. Helps in color recognition and motor skill development.",
        description_kn: "ಶಿಶುಗಳಿಗಾಗಿ ಸುರಕ್ಷಿತ, ವಿಷರಹಿತ ಶೈಕ್ಷಣಿಕ ರಿಂಗ್ಸ್. ಬಣ್ಣ ಗುರುತಿಸುವಿಕೆ ಮತ್ತು ಮೋಟಾರ್ ಕೌಶಲ್ಯ ಅಭಿವೃದ್ಧಿಗೆ ಸಹಾಯ ಮಾಡುತ್ತದೆ.",
        location: "Yarab Nagar, Channapatna",
        location_en: "Yarab Nagar, Channapatna",
        location_kn: "ಯಾರಬ್ ನಗರ, ಚನ್ನಪಟ್ಟಣ",
        giTagNumber: "GI-23-E02",
        imageUrl: "https://images.unsplash.com/photo-1596461404969-9ae70f2830c1?auto=format&fit=crop&q=80&w=800"
      },
      {
        id: "200003",
        name: "Heritage Train Engine",
        name_en: "Heritage Train Engine",
        name_kn: "ಪರಂಪರೆ ರೈಲು ಇಂಜಿನ್",
        artisanName: "Meeran",
        artisanName_en: "Meeran",
        artisanName_kn: "ಮೀರನ್",
        artisanId: "meeran_master",
        description: "A beautifully detailed train engine with a high-gloss lacquer finish. A favorite among young children.",
        description_en: "A beautifully detailed train engine with a high-gloss lacquer finish. A favorite among young children.",
        description_kn: "ಹೊಳೆಯುವ ಲ್ಯಾಕರ್ ಫಿನಿಶ್ ಹೊಂದಿರುವ ಸುಂದರವಾದ ರೈಲು ಇಂಜಿನ್. ಸಣ್ಣ ಮಕ್ಕಳಲ್ಲಿ ಬಹಳ ಜನಪ್ರಿಯ.",
        location: "Jeevanpur Mohalla, Channapatna",
        location_en: "Jeevanpur Mohalla, Channapatna",
        location_kn: "ಜೀವನಪುರ ಮೊಹಲ್ಲಾ, ಚನ್ನಪಟ್ಟಣ",
        giTagNumber: "GI-23-T03",
        imageUrl: "https://images.unsplash.com/photo-1515488042361-ee00e0ddd4e4?auto=format&fit=crop&q=80&w=800"
      },
      {
        id: "200004",
        name: "Traditional Spinning Top (Buguri)",
        name_en: "Traditional Spinning Top (Buguri)",
        name_kn: "ಸಾಂಪ್ರದಾಯಿಕ ಬುಗುರಿ",
        artisanName: "Shiva",
        artisanName_en: "Shiva",
        artisanName_kn: "ಶಿವ",
        artisanId: "shiva_master",
        description: "A classic street toy of India, redesigned with vibrant Channapatna lacquer. Perfectly balanced for long spins.",
        description_en: "A classic street toy of India, redesigned with vibrant Channapatna lacquer. Perfectly balanced for long spins.",
        description_kn: "ಭಾರತದ ಕ್ಲಾಸಿಕ್ ಬೀದಿ ಆಟಿಕೆ, ವೈವಿಧ್ಯಮಯ ಚನ್ನಪಟ್ಟಣ ಲ್ಯಾಕರ್‌ನೊಂದಿಗೆ ಮರುವಿನ್ಯಾಸಗೊಳಿಸಲಾಗಿದೆ. ದೀರ್ಘಕಾಲ ತಿರುಗಲು ಪರಿಪೂರ್ಣ ಸಮತೋಲನ ಹೊಂದಿದೆ.",
        location: "Crafts Park, Channapatna",
        location_en: "Crafts Park, Channapatna",
        location_kn: "ಕ್ರಾಫ್ಟ್ಸ್ ಪಾರ್ಕ್, ಚನ್ನಪಟ್ಟಣ",
        giTagNumber: "GI-23-B04",
        imageUrl: "https://images.unsplash.com/photo-1618842676088-c4d48a6a7c9d?auto=format&fit=crop&q=80&w=800"
      },
      {
        id: "200005",
        name: "Lacquered Jewelry Box",
        name_en: "Lacquered Jewelry Box",
        name_kn: "ಲ್ಯಾಕರ್ಡ್ ಆಭರಣ ಪೆಟ್ಟಿಗೆ",
        artisanName: "Ajmal",
        artisanName_en: "Ajmal",
        artisanName_kn: "ಅಜ್ಮಲ್",
        artisanId: "ajmal_artisan",
        description: "An elegant home decor item. Hand-carved with intricate patterns and finished with a mirror-like lacquer shine.",
        description_en: "An elegant home decor item. Hand-carved with intricate patterns and finished with a mirror-like lacquer shine.",
        description_kn: "ಸೊಗಸಾದ ಮನೆ ಅಲಂಕಾರಿಕ ವಸ್ತು. ಸಂಕೀರ್ಣ ವಿನ್ಯಾಸಗಳೊಂದಿಗೆ ಕೈಯಾರೆ ಕೆತ್ತಲಾಗಿದೆ ಮತ್ತು ಕನ್ನಡಿಯಂತಹ ಲ್ಯಾಕರ್ ಹೊಳಪಿನೊಂದಿಗೆ ಪೂರ್ಣಗೊಳಿಸಲಾಗಿದೆ.",
        location: "Crafts Park, Channapatna",
        location_en: "Crafts Park, Channapatna",
        location_kn: "ಕ್ರಾಫ್ಟ್ಸ್ ಪಾರ್ಕ್, ಚನ್ನಪಟ್ಟಣ",
        giTagNumber: "GI-23-D05",
        imageUrl: "https://images.unsplash.com/photo-1582555172866-f73bb12a2ab3?auto=format&fit=crop&q=80&w=800"
      },
      {
        id: "200006",
        name: "Eco-Friendly Pull Toy",
        name_en: "Eco-Friendly Pull Toy",
        name_kn: "ಪರಿಸರ ಸ್ನೇಹಿ ಪುಲ್ ಟಾಯ್",
        artisanName: "Parvati",
        artisanName_en: "Parvati",
        artisanName_kn: "ಪಾರ್ವತಿ",
        artisanId: "parvati_cluster",
        description: "Sustainable wood and natural dyes make this pull-along toy a safe companion for your child's first steps.",
        description_en: "Sustainable wood and natural dyes make this pull-along toy a safe companion for your child's first steps.",
        description_kn: "ಸುಸ್ಥಿರ ಮರ ಮತ್ತು ನೈಸರ್ಗಿಕ ಬಣ್ಣಗಳು ಈ ಪುಲ್-ಅಲಾಂಗ್ ಗೊಂಬೆಯನ್ನು ನಿಮ್ಮ ಮಗುವಿನ ಮೊದಲ ಹೆಜ್ಜೆಗಳಿಗೆ ಸುರಕ್ಷಿತ ಸಂಗಾತಿಯನ್ನಾಗಿ ಮಾಡುತ್ತವೆ.",
        location: "Kuvempu Nagar, Channapatna",
        location_en: "Kuvempu Nagar, Channapatna",
        location_kn: "ಕುವೆಂಪು ನಗರ, ಚನ್ನಪಟ್ಟಣ",
        giTagNumber: "GI-23-P06",
        imageUrl: "https://images.unsplash.com/photo-1537655780520-1e392ede8139?auto=format&fit=crop&q=80&w=800"
      },
      {
        id: "200007",
        name: "Contemporary Lacquer Lamp",
        name_en: "Contemporary Lacquer Lamp",
        name_kn: "ಸಮಕಾಲೀನ ಲ್ಯಾಕರ್ ದೀಪ",
        artisanName: "Karthik Vaidyanathan",
        artisanName_en: "Karthik Vaidyanathan",
        artisanName_kn: "ಕಾರ್ತಿಕ್ ವೈದ್ಯನಾಥನ್",
        artisanId: "karthik_vaidyanathan",
        description: "A designer lamp that blends 200-year-old techniques with a minimalist modern aesthetic.",
        description_en: "A designer lamp that blends 200-year-old techniques with a minimalist modern aesthetic.",
        description_kn: "200 ವರ್ಷಗಳ ಹಳೆಯ ತಂತ್ರಗಳನ್ನು ಕನಿಷ್ಠ ಆಧುನಿಕ ಸೌಂದರ್ಯದೊಂದಿಗೆ ಸಂಯೋಜಿಸುವ ಡಿಸೈನರ್ ದೀಪ.",
        location: "Heritage Street, Channapatna",
        location_en: "Heritage Street, Channapatna",
        location_kn: "ಪರಂಪರೆ ಬೀದಿ, ಚನ್ನಪಟ್ಟಣ",
        giTagNumber: "GI-23-L07",
        imageUrl: "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?auto=format&fit=crop&q=80&w=800"
      }
    ];

    for (const toy of toys) {
      await toysRef.doc(toy.id).set(toy);
    }
    console.log("Seeded 7 Authentic Toys with appropriate images.");

    // 3. Seed Workshops
    const workshopsRef = db.collection('workshops');
    const workshops = [
      {
        id: "ws-athar",
        name: "Bharath Art and Crafts Factory",
        name_en: "Bharath Art and Crafts Factory",
        name_kn: "ಭಾರತ್ ಕಲೆ ಮತ್ತು ಕರಕುಶಲ ಕಾರ್ಖಾನೆ",
        artisanName: "Syed Athar",
        artisanName_en: "Syed Athar",
        artisanName_kn: "ಸೈಯದ್ ಅಥರ್",
        description: "The town's most famous factory tour. See artisans working on traditional manual lathes using Hale wood.",
        description_en: "The town's most famous factory tour. See artisans working on traditional manual lathes using Hale wood.",
        description_kn: "ಪಟ್ಟಣದ ಅತ್ಯಂತ ಪ್ರಸಿದ್ಧ ಕಾರ್ಖಾನೆ ಪ್ರವಾಸ. ಕುಶಲಕರ್ಮಿಗಳು ಹಾಲೆ ಮರವನ್ನು ಬಳಸಿ ಸಾಂಪ್ರದಾಯಿಕ ಹಸ್ತಚಾಲಿತ ಲೇತ್‌ಗಳಲ್ಲಿ ಕೆಲಸ ಮಾಡುವುದನ್ನು ನೋಡಿ.",
        address: "Nizami Chouk, Channapatna, Karnataka 562160",
        address_en: "Nizami Chouk, Channapatna, Karnataka 562160",
        address_kn: "ನಿಜಾಮಿ ಚೌಕ್, ಚನ್ನಪಟ್ಟಣ, ಕರ್ನಾಟಕ 562160",
        distance: "0.2 km from Highway",
        latitude: 12.6525,
        longitude: 77.2090
      },
      {
        id: "ws-meeran",
        name: "Meeran Art & Crafts Studio",
        name_en: "Meeran Art & Crafts Studio",
        name_kn: "ಮೀರನ್ ಕಲೆ ಮತ್ತು ಕರಕುಶಲ ಸ್ಟುಡಿಯೋ",
        artisanName: "Meeran",
        artisanName_en: "Meeran",
        artisanName_kn: "ಮೀರನ್",
        description: "High-precision workshop focusing on export-quality nesting dolls and specialized lacquerware.",
        description_en: "High-precision workshop focusing on export-quality nesting dolls and specialized lacquerware.",
        description_kn: "ರಫ್ತು-ಗುಣಮಟ್ಟದ ನೆಸ್ಟಿಂಗ್ ಗೊಂಬೆಗಳು ಮತ್ತು ವಿಶೇಷ ಲ್ಯಾಕರ್‌ವೇರ್ ಮೇಲೆ ಕೇಂದ್ರೀಕರಿಸುವ ಹೆಚ್ಚಿನ ನಿಖರತೆಯ ಕಾರ್ಯಾಗಾರ.",
        address: "Jeevanpur Mohalla, Channapatna, Karnataka 562160",
        address_en: "Jeevanpur Mohalla, Channapatna, Karnataka 562160",
        address_kn: "ಜೀವನಪುರ ಮೊಹಲ್ಲಾ, ಚನ್ನಪಟ್ಟಣ, ಕರ್ನಾಟಕ 562160",
        distance: "1.0 km from Town Center",
        latitude: 12.6500,
        longitude: 77.2120
      },
      {
        id: "ws-salma",
        name: "Salma Toys Production Unit",
        name_en: "Salma Toys Production Unit",
        name_kn: "ಸಲ್ಮಾ ಗೊಂಬೆಗಳ ಉತ್ಪಾದನಾ ಘಟಕ",
        artisanName: "Noor Salma",
        artisanName_en: "Noor Salma",
        artisanName_kn: "ನೂರ್ ಸಲ್ಮಾ",
        description: "A community-focused unit employing local women to create safe and educational products.",
        description_en: "A community-focused unit employing local women to create safe and educational products.",
        description_kn: "ಸುರಕ್ಷಿತ ಮತ್ತು ಶೈಕ್ಷಣಿಕ ಉತ್ಪನ್ನಗಳನ್ನು ತಯಾರಿಸಲು ಸ್ಥಳೀಯ ಮಹಿಳೆಯರನ್ನು ಕೆಲಸಕ್ಕೆ ನೇಮಿಸಿಕೊಳ್ಳುವ ಸಮುದಾಯ-ಕೇಂದ್ರಿತ ಘಟಕ.",
        address: "Yarab Nagar, Channapatna, Karnataka 562160",
        address_en: "Yarab Nagar, Channapatna, Karnataka 562160",
        address_kn: "ಯಾರಬ್ ನಗರ, ಚನ್ನಪಟ್ಟಣ, ಕರ್ನಾಟಕ 562160",
        distance: "1.5 km from Station",
        latitude: 12.6540,
        longitude: 77.2050
      },
      {
        id: "ws-beereshwara",
        name: "Sri Beereshwara Arts Workshop",
        name_en: "Sri Beereshwara Arts Workshop",
        name_kn: "ಶ್ರೀ ಬೀರೇಶ್ವರ ಕಲಾ ಕಾರ್ಯಾಗಾರ",
        artisanName: "Shiva",
        artisanName_en: "Shiva",
        artisanName_kn: "ಶಿವ",
        description: "Located in the dedicated Crafts Park, this unit specializes in larger home decor and gift items.",
        description_en: "Located in the dedicated Crafts Park, this unit specializes in larger home decor and gift items.",
        description_kn: "ವಿಶೇಷ ಕ್ರಾಫ್ಟ್ಸ್ ಪಾರ್ಕ್‌ನಲ್ಲಿ ನೆಲೆಸಿರುವ ಈ ಘಟಕವು ಬೃಹತ್ ಮನೆ ಅಲಂಕಾರಿಕ ಮತ್ತು ಉಡುಗೊರೆ ವಸ್ತುಗಳಲ್ಲಿ ಪರಿಣತಿ ಹೊಂದಿದೆ.",
        address: "Crafts Park Plot #12, Channapatna, Karnataka 562160",
        address_en: "Crafts Park Plot #12, Channapatna, Karnataka 562160",
        address_kn: "ಕ್ರಾಫ್ಟ್ಸ್ ಪಾರ್ಕ್ ಪ್ಲಾಟ್ #12, ಚನ್ನಪಟ್ಟಣ, ಕರ್ನಾಟಕ 562160",
        distance: "2.0 km from Highway",
        latitude: 12.6480,
        longitude: 77.2150
      },
      {
        id: "ws-ajmal",
        name: "Ajmal Handicrafts Center",
        name_en: "Ajmal Handicrafts Center",
        name_kn: "ಅಜ್ಮಲ್ ಹ್ಯಾಂಡಿಕ್ರಾಫ್ಟ್ಸ್ ಕೇಂದ್ರ",
        artisanName: "Ajmal",
        artisanName_en: "Ajmal",
        artisanName_kn: "ಅಜ್ಮಲ್",
        description: "A creative space for miniature furniture and traditional storytelling dolls.",
        description_en: "A creative space for miniature furniture and traditional storytelling dolls.",
        description_kn: "ಕಿರು ಪೀಠೋಪಕರಣಗಳು ಮತ್ತು ಸಾಂಪ್ರದಾಯಿಕ ಕಥೆ ಹೇಳುವ ಗೊಂಬೆಗಳಿಗಾಗಿ ಒಂದು ಸೃಜನಶೀಲ ಸ್ಥಳ.",
        address: "Channapatna Crafts Park, Block B, Karnataka 562160",
        address_en: "Channapatna Crafts Park, Block B, Karnataka 562160",
        address_kn: "ಚನ್ನಪಟ್ಟಣ ಕ್ರಾಫ್ಟ್ಸ್ ಪಾರ್ಕ್, ಬ್ಲಾಕ್ ಬಿ, ಕರ್ನಾಟಕ 562160",
        distance: "2.1 km from Highway",
        latitude: 12.6475,
        longitude: 77.2155
      },
      {
        id: "ws-maya",
        name: "Maya Organic Artisan Cluster",
        name_en: "Maya Organic Artisan Cluster",
        name_kn: "ಮಾಯಾ ಆರ್ಗ್ಯಾನಿಕ್ ಕುಶಲಕರ್ಮಿಗಳ ಸಮೂಹ",
        artisanName: "Parvati",
        artisanName_en: "Parvati",
        artisanName_kn: "ಪಾರ್ವತಿ",
        description: "A cluster of household units working together for sustainable and fair-trade craft production.",
        description_en: "A cluster of household units working together for sustainable and fair-trade craft production.",
        description_kn: "ಸುಸ್ಥಿರ ಮತ್ತು ನ್ಯಾಯಯುತ ವ್ಯಾಪಾರ ಕರಕುಶಲ ಉತ್ಪಾದನೆಗಾಗಿ ಒಟ್ಟಾಗಿ ಕೆಲಸ ಮಾಡುವ ಗೃಹ ಘಟಕಗಳ ಸಮೂಹ.",
        address: "Kuvempu Nagar, Channapatna, Karnataka 562160",
        address_en: "Kuvempu Nagar, Channapatna, Karnataka 562160",
        address_kn: "ಕುವೆಂಪು ನಗರ, ಚನ್ನಪಟ್ಟಣ, ಕರ್ನಾಟಕ 562160",
        distance: "1.2 km from Main Road",
        latitude: 12.6560,
        longitude: 77.2110
      },
      {
        id: "ws-varnam",
        name: "Varnam Design Studio & Workshop",
        name_en: "Varnam Design Studio & Workshop",
        name_kn: "ವರ್ಣಂ ಡಿಸೈನ್ ಸ್ಟುಡಿಯೋ ಮತ್ತು ಕಾರ್ಯಾಗಾರ",
        artisanName: "Karthik Vaidyanathan",
        artisanName_en: "Karthik Vaidyanathan",
        artisanName_kn: "ಕಾರ್ತಿಕ್ ವೈದ್ಯನಾಥನ್",
        description: "The birthplace of contemporary Channapatna design. Blending tradition with modern aesthetics.",
        description_en: "The birthplace of contemporary Channapatna design. Blending tradition with modern aesthetics.",
        description_kn: "ಸಮಕಾಲೀನ ಚನ್ನಪಟ್ಟಣ ವಿನ್ಯಾಸದ ಜನ್ಮಸ್ಥಳ. ಸಂಪ್ರದಾಯವನ್ನು ಆಧುನಿಕ ಸೌಂದರ್ಯದೊಂದಿಗೆ ಸಂಯೋಜಿಸುತ್ತದೆ.",
        address: "Heritage Street, Near Channapatna Bazar, Karnataka 562160",
        address_en: "Heritage Street, Near Channapatna Bazar, Karnataka 562160",
        address_kn: "ಪರಂಪರೆ ಬೀದಿ, ಚನ್ನಪಟ್ಟಣ ಬಜಾರ್ ಹತ್ತಿರ, ಕರ್ನಾಟಕ 562160",
        distance: "0.8 km from Town Center",
        latitude: 12.6530,
        longitude: 77.2100
      }
    ];

    for (const workshop of workshops) {
      await workshopsRef.doc(workshop.id).set(workshop);
    }
    console.log("Seeded 7 Workshops.");

    console.log("Successfully seeded all collections with 7 genuine workshops and authentic catalog imagery!");
  } catch (error) {
    console.error("Error seeding Firestore:", error);
  }
}

seed();

const admin = require('firebase-admin');

admin.initializeApp({
  credential: admin.credential.applicationDefault(),
  projectId: 'channapatna-namma-pride-9324b'
});

const db = admin.firestore();

async function seed() {
  try {
    console.log("Seeding Firestore...");
    const toysRef = db.collection('toys');

    const toy1 = {
      id: "123456",
      name: "Rocking Horse",
      artisanName: "Ramesh Kumar",
      location: "Kala Nagar, Channapatna",
      description: "A classic Channapatna toy crafted from Hale wood and colored with natural vegetable dyes. The rocking horse is a staple of traditional Indian craftsmanship."
    };

    const toy2 = {
      id: "654321",
      name: "Stacking Rings",
      artisanName: "Lakshmi Devi",
      location: "Heritage Street, Channapatna",
      description: "Educational stacking rings made with non-toxic colors, perfect for infants and toddlers."
    };

    await toysRef.doc(toy1.id).set(toy1);
    await toysRef.doc(toy2.id).set(toy2);

    console.log("Successfully seeded Firestore with mock toys (IDs: 123456, 654321)!");
  } catch (error) {
    console.error("Error seeding Firestore:", error);
  }
}

seed();

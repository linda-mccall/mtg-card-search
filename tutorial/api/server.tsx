import { Server } from 'miragejs/server';
import { createServer } from "miragejs"

export type Card = {
  id: string;
  name: string;
  img: string;
};

const cards : Card[] = [
  { id: "1", name: "Birds of Paradise", img: "https://cards.scryfall.io/normal/front/3/d/3d69a3e0-6a2e-475a-964e-0affed1c017d.jpg?1722384747" },
  { id: "2", name: "Thrummingbird", img: "https://cards.scryfall.io/normal/front/8/6/86ff84d8-1b89-42cc-a8c0-4ac6fa26ce65.jpg?1690004460" },
  { id: "3", name: "Sunbird's Invocation", img: "https://cards.scryfall.io/normal/front/7/5/75c1eb6a-2183-4f60-b075-f7289df35be7.jpg?1625194085" },
  { id: "4", name: "Mockingbird", img: "https://cards.scryfall.io/normal/front/a/d/ade32396-8841-4ba4-8852-d11146607f21.jpg?1722388218" },
  { id: "5", name: "Sunbird Standard", img: "https://cards.scryfall.io/normal/front/e/0/e0b6d40a-fded-4625-b03c-765c88d75766.jpg?1699044640" },
  { id: "6", name: "Brotherhood Vertibird", img: "https://cards.scryfall.io/normal/front/c/2/c207c76d-5f10-4fd9-9826-cf0611bfa2d0.jpg?1708742708" },
  { id: "7", name: "Bird Admirer", img: "https://cards.scryfall.io/normal/front/7/1/71ccc444-54c8-4f7c-a425-82bc3eea1eb0.jpg?1715666727" },
  { id: "8", name: "Akoum Firebird", img: "https://cards.scryfall.io/normal/front/3/0/304a2e4c-da2c-43fb-b44d-9db55ff94f04.jpg?1562906305" },
  { id: "9", name: "Skarrgan Firebird", img: "https://cards.scryfall.io/normal/front/b/9/b9d52c11-f955-489e-b3c9-5a7e1a7933e8.jpg?1592765736" },
  { id: "10", name: "Seller of Songbirds", img: "https://cards.scryfall.io/normal/front/d/f/df84d9ce-9ff0-438a-96e1-2aadd60dcaba.jpg?1706239762" },
  { id: "11", name: "Molten Firebird", img: "https://cards.scryfall.io/normal/front/d/d/dddda66c-2df4-452e-8eca-7e100213fd98.jpg?1562583771" },
  { id: "12", name: "Matsu-Tribe Birdstalker", img: "https://cards.scryfall.io/normal/front/f/1/f1afb0c0-e138-4d2b-b3aa-fa40778f8d79.jpg?1562496617" },
];  

export default function mockServer () {
  createServer({
    routes() {

      this.get("/api/cards", (schema, request) => {
        console.log(request);
        const search = request.queryParams.search?.toString().toLowerCase() || null;
        return search ? cards.filter((card) => card.name.toLowerCase().includes(search)) : null;
      }
    );

    this.get("/api/cards/:id", (schema, request) => {
      console.log(request);
      const { id } = request.params;
      const card = cards.find((c) => c.id === id);
      return card ? card : null;
    });
  },
  })
}

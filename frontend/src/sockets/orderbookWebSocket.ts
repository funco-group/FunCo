import { v4 as uuidv4 } from "uuid";
import { OrderType } from "@/interfaces/CryptoType";

export default function orderbookWebSockets(
  setOrderList: React.Dispatch<React.SetStateAction<OrderType[]>>,
) {
  const uuid = uuidv4();
  const handlers = {
    ws: null as WebSocket | null,
    status: -1 as number | undefined,

    connect() {
      this.ws = new WebSocket("wss://api.upbit.com/websocket/v1");
      this.ws.onopen = this.onopen.bind(this);
      this.ws.onmessage = this.onmessage.bind(this);
      this.ws.onerror = this.onerror.bind(this);
      this.status = this.ws.readyState;
    },

    onopen() {
      console.log("orderbook connected!");
      this.status = this.ws?.readyState;
    },

    send(code: string) {
      console.log("orderbook send!");
      const message = JSON.stringify([
        {
          ticket: uuid,
        },
        {
          type: "orderbook",
          codes: JSON.parse(code),
        },
      ]);
      this.ws!.send(message);
    },

    onmessage(event: MessageEvent) {
      if (event.data instanceof Blob) {
        event.data.arrayBuffer().then((data) => {
          const decoder = new TextDecoder("utf-8");
          const priceData = decoder.decode(data);
          const priceJson = JSON.parse(priceData);
          setOrderList(priceJson.orderbook_units.slice(0, 9));
        });
      }
    },

    onerror() {
      this.status = this.ws?.readyState;
      console.error("WebSocket error");
    },

    close() {
      console.log("orderbook socket close");
      this.ws?.close();
      this.status = this.ws?.readyState;
    },
  };

  handlers.connect();

  return handlers;
}

import { v4 as uuidv4 } from "uuid";
import { PriceType } from "@/interfaces/PriceWindowType";

export default function tickerWebSocket(
  setPriceList: React.Dispatch<React.SetStateAction<PriceType[]>>,
  setStatus: React.Dispatch<React.SetStateAction<number | undefined>>,
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
      setStatus(this.ws.readyState);
    },

    onopen() {
      console.log("ticker connected!");
      this.status = this.ws?.readyState;
      setStatus(this.ws?.readyState);
    },

    send(codes: string) {
      console.log("ticker send!");
      const message = JSON.stringify([
        {
          ticket: uuid,
        },
        {
          type: "ticker",
          codes: JSON.parse(codes),
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

          setPriceList((prevPrice) => {
            return prevPrice.map((price) =>
              price.code === priceJson.code
                ? {
                    ...price,
                    tradePrice: priceJson.trade_price,
                    change: priceJson.change,
                    signedChangeRate: priceJson.signed_change_rate,
                    signedChangePrice: priceJson.signed_change_price,
                    accTradeVolme24h: priceJson.acc_trade_volume_24h,
                    accTradePrice24h: priceJson.acc_trade_price_24h,
                    highPrice: priceJson.high_price,
                    lowPrice: priceJson.low_price,
                    updated: true,
                  }
                : price,
            );
          });

          setTimeout(() => {
            setPriceList((prevPrice) =>
              prevPrice.map((price) =>
                price.code === priceJson.code
                  ? { ...price, updated: false }
                  : price,
              ),
            );
          }, 1000);
        });
      }
    },

    onerror() {
      console.error("WebSocket error");
      this.status = this.ws?.readyState;
      setStatus(this.ws?.readyState);
    },

    close() {
      this.ws?.close();
      console.log("ticker socket close");
      this.status = this.ws?.readyState;
      setStatus(this.ws?.readyState);
    },
  };

  handlers.connect();

  return handlers;
}

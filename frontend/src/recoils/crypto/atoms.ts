import { atom, AtomEffect } from "recoil";
import { PriceType } from "@/interfaces/PriceWindowType";

const localStorageEffect: (key: string) => AtomEffect<PriceType[]> =
  (key) =>
  ({ setSelf, onSet }) => {
    const savedValue = localStorage.getItem(key);
    if (savedValue !== null) {
      setSelf(JSON.parse(savedValue));
    }

    onSet((newValue, _, isReset) =>
      isReset
        ? localStorage.removeItem(key)
        : localStorage.setItem(key, JSON.stringify(newValue)),
    );
  };

const priceListState = atom<PriceType[]>({
  key: "priceListState",
  default: [
    {
      code: "",
      koreanName: "",
      tradePrice: 0,
      change: "EVEN",
      signedChangeRate: 0,
      signedChangePrice: 0,
      accTradeVolme24h: 0,
      accTradePrice24h: 0,
      highPrice: 0,
      lowPrice: 0,
      updated: false,
    },
  ],
  effects: [localStorageEffect("market")],
});

export default priceListState;

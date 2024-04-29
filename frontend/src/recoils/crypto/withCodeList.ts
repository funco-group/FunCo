import { selector } from "recoil";
import priceListState from "./atoms";

// 코드 리스트 추출하는 selector
const codeListState = selector({
  key: "codeListState",
  get: ({ get }) => {
    const priceList = get(priceListState);
    const codes = priceList!.map((price) => price.code);
    return codes;
  },
});

export default codeListState;

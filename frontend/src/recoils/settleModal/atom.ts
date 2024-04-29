import { atom } from "recoil";
import { SettleModalStateType } from "@/interfaces/tradeHistory/follow/SettleModalStateType";

const settleModalState = atom<SettleModalStateType>({
  key: "settleModalState",
  default: {
    settleModal: null,
  },
});

export default settleModalState;

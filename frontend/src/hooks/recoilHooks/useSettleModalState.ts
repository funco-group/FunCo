import { useRecoilState } from "recoil";
import { SettleModalType } from "@/interfaces/tradeHistory/follow/SettleModalType";
import settleModalState from "@/recoils/settleModal";

function useSettleModalState() {
  const [{ settleModal }, setSettleModalState] =
    useRecoilState(settleModalState);
  return {
    settleModal,
    onModal: (newSettleModal: SettleModalType) => {
      setSettleModalState({ settleModal: newSettleModal });
    },
    offModal: () => setSettleModalState({ settleModal: null }),
  };
}

export default useSettleModalState;

import { atom } from "recoil";
import { FollowModalStateType } from "@/interfaces/userPage/FollowModalStateType";

const followModalState = atom<FollowModalStateType>({
  key: "followModalState",
  default: {
    followModal: null,
  },
});

export default followModalState;

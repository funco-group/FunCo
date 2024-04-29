import { atom } from "recoil";
import { LoginAlertModalStateType } from "@/interfaces/LoginAlertModalStateType";

const LoginAlertModalState = atom<LoginAlertModalStateType>({
  key: "LoginAlertModalState",
  default: {
    loginAlertModal: false,
  },
});

export default LoginAlertModalState;

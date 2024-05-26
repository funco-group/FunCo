import { useRecoilState } from "recoil";
import LoginAlertModalState from "@/recoils/loginAlertModal";

function useLoginAlertModalState() {
  const [{ loginAlertModal }, setLoginAlertModalState] =
    useRecoilState(LoginAlertModalState);

  return {
    loginAlertModal,
    onLoginAlertModal: () => setLoginAlertModalState({ loginAlertModal: true }),
    offLoginAlertModal: () =>
      setLoginAlertModalState({ loginAlertModal: false }),
  };
}

export default useLoginAlertModalState;

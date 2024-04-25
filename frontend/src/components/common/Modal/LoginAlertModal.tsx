import useLoginAlertModalState from "@/hooks/recoilHooks/useLoginAlertModalState";
import AlertModal from "./AlertModal";

function LoginAlertModal() {
  const { loginAlertModal, offLoginAlertModal } = useLoginAlertModalState();

  const handleCheckClick = () => {
    offLoginAlertModal();
  };

  if (!loginAlertModal) {
    return null;
  }
  return (
    <div>
      <AlertModal
        title="알림"
        content="로그인이 필요합니다."
        closeAlert={handleCheckClick}
      />
    </div>
  );
}

export default LoginAlertModal;

import { Dispatch, SetStateAction, useEffect, useState } from "react";
import {
  SettleModalBackgroundContainer,
  SettleModalContainer,
  SettleModalContentButtonRowDiv,
  SettleModalContentDiv,
  SettleModalContentRowDiv,
  SettleModalTitleDiv,
} from "./SettleModal.styled";
import useSettleModalState from "@/hooks/recoilHooks/useSettleModalState";
import BrandButtonComponent from "@/components/common/Button/BrandButtonComponent";
import palette from "@/lib/palette";
import { removeFollow } from "@/apis/follow";
import AlertModal from "@/components/common/Modal/AlertModal";
import { FollowingType } from "@/interfaces/tradeHistory/follow/FollowingTyps";

interface SettleModalProps {
  followings: FollowingType[];
  setFollowings: Dispatch<SetStateAction<FollowingType[] | undefined>>;
}

function SettleModal({ followings, setFollowings }: SettleModalProps) {
  const [alert, setAlert] = useState<boolean>(false);
  const [alertContent, setAlertContent] = useState<string>("");

  const { settleModal, offModal } = useSettleModalState();

  useEffect(() => {
    if (!alert && alertContent === "정산이 완료되었습니다.") {
      offModal();
    }
  }, [alert]);

  if (!settleModal) {
    return null;
  }

  const handleCancelClick = () => {
    offModal();
  };

  const handleSettleClick = () => {
    removeFollow(settleModal?.followId, () => {
      setFollowings(
        followings.filter(
          (following) => following.followId !== settleModal.followId,
        ),
      );
      setAlertContent("정산이 완료되었습니다.");
      setAlert(true);
    });
  };

  const closeAlert = () => {
    setAlert(false);
  };

  if (!settleModal) {
    return null;
  }
  return (
    <SettleModalBackgroundContainer>
      {alert && (
        <AlertModal
          title="알림"
          content={alertContent}
          closeAlert={closeAlert}
        />
      )}
      <SettleModalContainer>
        <SettleModalTitleDiv>정산</SettleModalTitleDiv>
        <SettleModalContentDiv>
          <SettleModalContentRowDiv>
            <div>금액</div>
            <div>
              <span>{settleModal.estimatedValue.toLocaleString("ko-KR")}</span>{" "}
              WON
            </div>
          </SettleModalContentRowDiv>
          <SettleModalContentRowDiv>
            <div>- 수수료</div>
            <div>
              <span>{settleModal.commission.toLocaleString("ko-KR")}</span> WON
            </div>
          </SettleModalContentRowDiv>
          <SettleModalContentRowDiv>
            <div>최종 정산 금액</div>
            <div>
              <span>
                {(
                  settleModal.estimatedValue - settleModal.commission
                ).toLocaleString("ko-KR")}
              </span>{" "}
              WON
            </div>
          </SettleModalContentRowDiv>
        </SettleModalContentDiv>
        <SettleModalContentButtonRowDiv>
          <BrandButtonComponent
            content="취소"
            color={null}
            cancel
            onClick={handleCancelClick}
            disabled={false}
          />
          <BrandButtonComponent
            content="정산"
            color={palette.brandBlue}
            cancel={false}
            onClick={handleSettleClick}
            disabled={false}
          />
        </SettleModalContentButtonRowDiv>
      </SettleModalContainer>
    </SettleModalBackgroundContainer>
  );
}

export default SettleModal;

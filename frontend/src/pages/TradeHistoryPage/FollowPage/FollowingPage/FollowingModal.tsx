import BrandButtonComponent from "@/components/common/Button/BrandButtonComponent";
import {
  SettleModalBackgroundContainer,
  SettleModalContainer,
  SettleModalContentButtonRowDiv,
  SettleModalTitleDiv,
} from "./SettleModal.styled";
import { OpenOrderContentDiv } from "../../OpenOrdersPage/OpenOrderContent.styled";

interface FollowingModalProps {
  title: string;
  handleClick: () => void;
  children: React.ReactNode;
}

function FollowingModal({ title, handleClick, children }: FollowingModalProps) {
  return (
    <SettleModalBackgroundContainer>
      <SettleModalContainer>
        <SettleModalTitleDiv>{title}</SettleModalTitleDiv>
        <OpenOrderContentDiv>{children}</OpenOrderContentDiv>
        <SettleModalContentButtonRowDiv>
          {/* <BrandButtonComponent
            content="취소"
            color={null}
            cancel
            onClick={handleClick}
            disabled={false}
          /> */}
          <BrandButtonComponent
            content="확인"
            color={null}
            cancel={false}
            onClick={handleClick}
            disabled={false}
          />
        </SettleModalContentButtonRowDiv>
      </SettleModalContainer>
    </SettleModalBackgroundContainer>
  );
}

export default FollowingModal;

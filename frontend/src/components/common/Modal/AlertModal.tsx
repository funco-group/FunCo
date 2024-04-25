import {
  SettleModalBackgroundContainer,
  SettleModalContainer,
  SettleModalContentButtonRowDiv,
} from "@/pages/TradeHistoryPage/FollowPage/FollowingPage/SettleModal.styled";
import BrandButtonComponent from "../Button/BrandButtonComponent";
import { ContentDiv, TitleDiv } from "./AlertModal.styled";

interface AlertModalProps {
  title: string;
  content: string;
  closeAlert: () => void;
}

function AlertModal({ title, content, closeAlert }: AlertModalProps) {
  return (
    <SettleModalBackgroundContainer>
      <SettleModalContainer>
        <TitleDiv>{title}</TitleDiv>
        <ContentDiv>{content}</ContentDiv>
        <SettleModalContentButtonRowDiv>
          <BrandButtonComponent
            content="확인"
            color={null}
            cancel={false}
            onClick={closeAlert}
            disabled={false}
          />
        </SettleModalContentButtonRowDiv>
      </SettleModalContainer>
    </SettleModalBackgroundContainer>
  );
}

export default AlertModal;

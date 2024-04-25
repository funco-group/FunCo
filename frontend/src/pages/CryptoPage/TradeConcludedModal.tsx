import BrandButtonComponent from "@/components/common/Button/BrandButtonComponent";
import {
  SettleModalBackgroundContainer,
  SettleModalContainer,
  SettleModalContentButtonRowDiv,
  SettleModalContentDiv,
  SettleModalContentRowDiv,
  SettleModalTitleDiv,
} from "../TradeHistoryPage/FollowPage/FollowingPage/SettleModal.styled";
import { TradeResultType } from "@/interfaces/TradeType";
import TypeDiv from "./TradeConcludedModal.styled";

interface TradeConcludedModalProps {
  result: TradeResultType;
  name: string;
  clickClose: () => void;
}

function TradeConcludedModal({
  result,
  name,
  clickClose,
}: TradeConcludedModalProps) {
  return (
    <SettleModalBackgroundContainer>
      <SettleModalContainer>
        <SettleModalTitleDiv>체결 완료</SettleModalTitleDiv>
        <SettleModalContentDiv>
          <SettleModalContentRowDiv>
            <div>코인</div>
            <div>
              <span>{result.ticker.split("-")[1]}</span>
            </div>
          </SettleModalContentRowDiv>
          <SettleModalContentRowDiv>
            <div>종류</div>
            <TypeDiv name={name}>{name}</TypeDiv>
          </SettleModalContentRowDiv>
          <SettleModalContentRowDiv>
            <div>거래 수량</div>
            <div>
              <span>{result.volume}</span>
            </div>
          </SettleModalContentRowDiv>
          <SettleModalContentRowDiv>
            <div>거래 단가</div>
            <div>
              <span>{result.price.toLocaleString("ko-KR")}</span> WON
            </div>
          </SettleModalContentRowDiv>
          <SettleModalContentRowDiv>
            <div>거래 금액</div>
            <div>
              <span>
                {(result.price * result.volume).toLocaleString("ko-KR")}
              </span>{" "}
              WON
            </div>
          </SettleModalContentRowDiv>
        </SettleModalContentDiv>
        <SettleModalContentButtonRowDiv>
          <BrandButtonComponent
            content="확인"
            color={null}
            cancel={false}
            onClick={clickClose}
            disabled={false}
          />
        </SettleModalContentButtonRowDiv>
      </SettleModalContainer>
    </SettleModalBackgroundContainer>
  );
}

export default TradeConcludedModal;

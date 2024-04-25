import { Dispatch, SetStateAction, useEffect, useState } from "react";
import { Tooltip } from "react-tooltip";
import useFollowModalState from "@/hooks/recoilHooks/useFollowModalState";
import {
  SettleModalBackgroundContainer,
  SettleModalContainer,
  SettleModalContentButtonRowDiv,
  SettleModalTitleDiv,
} from "../TradeHistoryPage/FollowPage/FollowingPage/SettleModal.styled";
import {
  FollowModalContentDiv,
  FollowModalTermsDiv,
  CashDiv,
  InputDiv,
  InvestDiv,
  ValidationText,
} from "./FollowModal.styled";
import BrandButtonComponent from "@/components/common/Button/BrandButtonComponent";
import palette from "@/lib/palette";
import BrandButton from "@/components/common/Button/BrandButtonComponent.styled";
import { addFollow } from "@/apis/follow";
import { getCash } from "@/apis/asset";
import AlertModal from "@/components/common/Modal/AlertModal";
import MemberType from "@/interfaces/userPage/MemberType";

interface FollowModalProps {
  member: MemberType;
  setMember: Dispatch<SetStateAction<MemberType | undefined>>;
}

function FollowModal({ member, setMember }: FollowModalProps) {
  const { followModal, offFollowModal } = useFollowModalState();
  const [cash, setCash] = useState<number>(0);
  const [investment, setinvestment] = useState("");
  const [isCheckTerms, setIsCheckTerms] = useState(false);
  const [validation, setValidation] = useState<boolean>(false);
  const [alert, setAlert] = useState<boolean>(false);
  const [alertContent, setAlertContent] = useState<string>("");

  useEffect(() => {
    getCash((res) => {
      const { data } = res;
      setCash(data.cash);
    });
  }, []);

  useEffect(() => {
    if (!alert && alertContent === "팔로우를 시작합니다.") {
      offFollowModal();
    }
  }, [alert]);

  if (!followModal) {
    return null;
  }

  const closeAlert = () => {
    setAlert(false);
  };

  const handleInvestmentInput = (e: React.ChangeEvent<HTMLInputElement>) => {
    const newInvestment = e.target.value;
    const newInvestmentNum = Number(newInvestment.replace(/,/g, ""));

    if (newInvestmentNum <= cash) {
      setValidation(false);
    }

    if (Number.isNaN(newInvestmentNum)) {
      setinvestment("0");
    } else if (newInvestmentNum > cash) {
      console.log("Hello");
      setValidation(true);
    }
    setinvestment(newInvestmentNum.toLocaleString("ko-KR"));
  };

  useEffect(() => {}, [investment]);

  const handleCheckInputClick = () => {
    setIsCheckTerms((prev) => !prev);
  };

  const handleCancelClick = () => {
    offFollowModal();
  };

  const handleFollowClick = () => {
    addFollow(
      {
        memberId: followModal?.memberId,
        investment: Number(investment.replace(/,/g, "")),
      },
      () => {
        setMember({ ...member, isFollow: true });
        setAlertContent("팔로우를 시작합니다.");
        setAlert(true);
      },
    );
  };

  return (
    <SettleModalBackgroundContainer>
      {alert && (
        <AlertModal
          title="알림"
          content={alertContent}
          closeAlert={closeAlert}
        />
      )}
      <SettleModalContainer width="30rem">
        <SettleModalTitleDiv>팔로우</SettleModalTitleDiv>
        <FollowModalContentDiv>
          <FollowModalTermsDiv>
            {`[팔로우]
            '팔로우' 버튼을 클릭하면 입력한 투자 금액만큼 해당 유저를 팔로우합니다. 
            투자된 금액은 본인이 팔로우한 유저가 직접 거래할 때의 총 자산 대비 비율에 맞추어 자동 투자됩니다.
            (예시 : 팔로우한 유저가 총 자산 1,000만원을 가지고 있고, 본인이 50만원을 
            팔로우하는 상황을 가정합니다. 만약 팔로우한 유저가 1,000만원 중 30%만큼 
            비트코인을 매수/매도하면, 본인의 투자 금액 50만원 중 30%가 그에 맞추어
            매수/매도됩니다.)\n
            [정산하기] 
            '정산하기' 버튼을 통해 정산할 수 있으며, 정산 시 수익금에 대해서 3%의 
            수수료를 제하고 가용 현금에 반영됩니다. (손실에 대해서는 수수료 없음) 
            수익금에서 제한 3%의 수수료는 본인이 팔로우한 유저에게 지급됩니다.`}
          </FollowModalTermsDiv>
          <label htmlFor="check">
            <input
              className="checkInput"
              type="checkbox"
              id="check"
              onChange={handleCheckInputClick}
            />{" "}
            약관에 동의합니다.
          </label>
          <CashDiv>
            가용 자산 : {cash?.toLocaleString("en-US")} <span>WON</span>{" "}
          </CashDiv>
          <InvestDiv>
            <InputDiv $validation={validation}>
              <input
                type="text"
                value={investment}
                onChange={handleInvestmentInput}
                placeholder="투자 금액"
              />
              <span>WON</span>
            </InputDiv>
          </InvestDiv>
          <ValidationText $validation={validation}>
            ※ 가용 자산을 넘겨서 투자할 수 없습니다.
          </ValidationText>
        </FollowModalContentDiv>
        <SettleModalContentButtonRowDiv>
          <BrandButtonComponent
            content="취소"
            color={null}
            cancel
            onClick={handleCancelClick}
            disabled={false}
          />
          <BrandButton
            color={null}
            $cancel={false}
            onClick={handleFollowClick}
            disabled={!isCheckTerms}
            data-tooltip-content="약관에 동의해주세요"
            data-tooltip-id={isCheckTerms ? "" : "buttonTooltip"}
          >
            팔로우
          </BrandButton>
          <Tooltip
            id="buttonTooltip"
            style={{ backgroundColor: palette.brandColor }}
            arrowColor="transparent"
            place="top"
            float
          />
        </SettleModalContentButtonRowDiv>
      </SettleModalContainer>
    </SettleModalBackgroundContainer>
  );
}

export default FollowModal;

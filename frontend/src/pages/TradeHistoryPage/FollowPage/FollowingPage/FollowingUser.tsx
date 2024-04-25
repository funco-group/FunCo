import { useState } from "react";
import { ComputedFollowingType } from "@/interfaces/tradeHistory/follow/ComputedFollowingType";
import {
  FollowingColumnGridDiv,
  FollowingUserContainer,
  FollowingTitleDiv,
  FollowingDetailDiv,
  FollowingDetailFlexDiv,
  FollowingDetailInnerDiv,
  FollowingContentDiv,
  FollowingDateDiv,
  FollowingContentMarginDiv,
  FollowingButtonDiv,
  FollowingLeftButtonDiv,
  FollowingUserGraphDiv,
  FollowingUserGraphInnerDiv,
} from "./FollowingUser.styled";
import {
  ColumnContainer,
  ColumnGrid,
  ColumnTitleDiv,
} from "@/styles/CommonStyled";
import useParseDate from "@/hooks/useParseDate";
import BrandButtonComponent from "@/components/common/Button/BrandButtonComponent";
import palette from "@/lib/palette";
import MonochromePieChart from "@/components/common/Chart/MonochromePieChart";
import useSettleModalState from "@/hooks/recoilHooks/useSettleModalState";
import TradeHistoryModal from "./TradeHistoryModal";

interface FollowingUserProps {
  followingUser: ComputedFollowingType;
}

function FollowingUser({ followingUser }: FollowingUserProps) {
  const { onModal } = useSettleModalState();
  const columnList = ["날짜", "투자 금액", "예상 금액", "수익률"];
  const followDate = useParseDate(followingUser.date).split(" ").join("\n");
  const estimatedProfitRate = (
    ((followingUser.estimatedValue - followingUser.investment) /
      followingUser.investment) *
    100
  ).toFixed(2);
  const investmentList: (string | number)[][] = [["현금", followingUser.cash]];
  followingUser.coins.forEach((coin) => {
    const coinPrice = [coin.ticker, coin.price.toFixed(3)];
    investmentList.push(coinPrice);
  });
  const [onTradeModal, setOnTradeModal] = useState(false);

  const handleTradeHistoryClick = () => {
    setOnTradeModal((prev) => !prev);
  };

  const handleSettleClick = () => {
    const commission =
      followingUser.estimatedValue - followingUser.investment > 0
        ? Math.round(
            (followingUser.estimatedValue - followingUser.investment) * 0.03,
          )
        : 0;
    onModal({
      followId: followingUser.followId,
      estimatedValue: followingUser.estimatedValue,
      commission,
    });
  };

  return (
    <FollowingUserContainer>
      {onTradeModal && (
        <TradeHistoryModal
          handleTradeHistoryClick={handleTradeHistoryClick}
          followId={followingUser.followId}
        />
      )}
      <FollowingTitleDiv>{followingUser.nickname}</FollowingTitleDiv>
      <FollowingDetailFlexDiv>
        <FollowingDetailDiv>
          <FollowingDetailInnerDiv>
            <ColumnContainer>
              <ColumnGrid column="7.5rem 1fr 1fr 1fr">
                {columnList.map((column) => (
                  <ColumnTitleDiv key={column}>{column}</ColumnTitleDiv>
                ))}
              </ColumnGrid>
            </ColumnContainer>
            <FollowingContentDiv>
              <FollowingColumnGridDiv>
                <FollowingDateDiv>{followDate}</FollowingDateDiv>
                <FollowingContentMarginDiv color="black">
                  <span>
                    {followingUser.investment.toLocaleString("ko-KR")}
                  </span>{" "}
                  WON
                </FollowingContentMarginDiv>
                <FollowingContentMarginDiv color="black">
                  <span>
                    {followingUser.estimatedValue.toLocaleString("ko-KR")}
                  </span>{" "}
                  WON
                </FollowingContentMarginDiv>
                <FollowingContentMarginDiv
                  color={estimatedProfitRate.startsWith("-") ? "blue" : "red"}
                >
                  <span>{estimatedProfitRate}</span> %
                </FollowingContentMarginDiv>
              </FollowingColumnGridDiv>
            </FollowingContentDiv>
          </FollowingDetailInnerDiv>
        </FollowingDetailDiv>
        <FollowingUserGraphDiv>
          <FollowingUserGraphInnerDiv>
            <MonochromePieChart
              investmentList={investmentList}
              isLegend={false}
            />
          </FollowingUserGraphInnerDiv>
        </FollowingUserGraphDiv>
      </FollowingDetailFlexDiv>
      <FollowingButtonDiv>
        <FollowingLeftButtonDiv>
          <BrandButtonComponent
            color={null}
            content="거래 내역 보기"
            cancel={false}
            onClick={handleTradeHistoryClick}
            disabled={false}
          />
        </FollowingLeftButtonDiv>
        <BrandButtonComponent
          color={palette.brandBlue}
          content="정산하기"
          cancel={false}
          onClick={handleSettleClick}
          disabled={false}
        />
      </FollowingButtonDiv>
    </FollowingUserContainer>
  );
}

export default FollowingUser;

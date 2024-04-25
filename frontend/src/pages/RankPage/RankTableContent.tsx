import { useNavigate } from "react-router-dom";
import { RankType } from "@/interfaces/rank/RankType";
import { RankTableColumnGridDiv } from "./RankTable.styled";
import {
  RankSpan,
  RankTableContentContainer,
  RankTableContentMarginDiv,
  RankTableContentUserDiv,
} from "./RankTableContent.styled";
import { MoneySpan, RateSpan } from "./TopRankContent.styled";
import { FollowingContentMarginDiv } from "../TradeHistoryPage/FollowPage/FollowingPage/FollowingUser.styled";
import medalMap from "@/lib/medalMap";

interface RankTableContentProps {
  rank: RankType;
  nowTabName: string;
}

function RankTableContent({ rank, nowTabName }: RankTableContentProps) {
  const navigate = useNavigate();

  const handleProfileClick = () => {
    navigate(`/member/${rank.member.id}`);
  };
  return (
    <RankTableContentContainer>
      <RankTableColumnGridDiv>
        <FollowingContentMarginDiv color="">
          <RankSpan $isTopRank={medalMap.get(rank.rank)}>
            {medalMap.get(rank.rank) || rank.rank}
          </RankSpan>
        </FollowingContentMarginDiv>
        <div>
          <RankTableContentUserDiv onClick={handleProfileClick}>
            <img
              src={rank.member.profileUrl}
              alt="rank-user-profile"
              draggable={false}
            />
            <div>{rank.member.nickname}</div>
          </RankTableContentUserDiv>
        </div>
        <RankTableContentMarginDiv>
          <RateSpan
            $isProfit={
              rank.returnRate > 0
                ? "red"
                : rank.returnRate < 0
                  ? "blue"
                  : "black"
            }
          >
            {rank.returnRate} %
          </RateSpan>
        </RankTableContentMarginDiv>
        <RankTableContentMarginDiv>
          <MoneySpan $active={nowTabName === "follow"}>
            {rank.followingAsset.toLocaleString("en-US")} WON
          </MoneySpan>
        </RankTableContentMarginDiv>
        <RankTableContentMarginDiv>
          <MoneySpan $active={nowTabName === "asset"}>
            {rank.totalAsset.toLocaleString("en-US")} WON
          </MoneySpan>
        </RankTableContentMarginDiv>
      </RankTableColumnGridDiv>
    </RankTableContentContainer>
  );
}

export default RankTableContent;

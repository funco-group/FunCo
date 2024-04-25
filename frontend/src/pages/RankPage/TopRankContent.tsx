import { useNavigate } from "react-router-dom";
import { RankType } from "@/interfaces/rank/RankType";
import {
  MoneySpan,
  RateSpan,
  TopRankContentContainer,
  TopRankContentStatDiv,
  TopRankProfileDiv,
} from "./TopRankContent.styled";
import medalMap from "@/lib/medalMap";

interface TopRankContentProps {
  topRank: RankType;
  nowTapName: string;
}

function TopRankContent({ topRank, nowTapName }: TopRankContentProps) {
  const navigate = useNavigate();

  const handleProfileClick = () => {
    navigate(`/member/${topRank.member.id}`);
  };
  return (
    <TopRankContentContainer>
      <TopRankProfileDiv
        $isTop={topRank.rank === 1}
        onClick={handleProfileClick}
      >
        <img
          src={topRank.member.profileUrl}
          alt="top-rank-profile"
          draggable={false}
        />
        <div>
          <span>{medalMap.get(topRank.rank)}</span>
        </div>
        <div>{topRank.member.nickname}</div>
      </TopRankProfileDiv>
      <TopRankContentStatDiv>
        <div>수익률</div>
        <div>
          <RateSpan
            $isProfit={
              topRank.returnRate > 0
                ? "red"
                : topRank.returnRate < 0
                  ? "blue"
                  : "black"
            }
          >
            {topRank.returnRate}
          </RateSpan>{" "}
          %
        </div>
      </TopRankContentStatDiv>
      <TopRankContentStatDiv>
        <div>따라오는 금액</div>
        <div>
          <MoneySpan $active={nowTapName === "follow"}>
            {topRank.followingAsset.toLocaleString("en-US")}
          </MoneySpan>{" "}
          WON
        </div>
      </TopRankContentStatDiv>
      <TopRankContentStatDiv>
        <div>총 자산</div>
        <div>
          <MoneySpan $active={nowTapName === "asset"}>
            {topRank.totalAsset.toLocaleString("en-US")}
          </MoneySpan>{" "}
          WON
        </div>
      </TopRankContentStatDiv>
    </TopRankContentContainer>
  );
}

export default TopRankContent;

import { RankType } from "@/interfaces/rank/RankType";
import { TopRankContainer } from "./TopRank.styled";
import TopRankContent from "./TopRankContent";

interface TopRankProps {
  topRankList: RankType[];
  nowTabName: string;
}

function TopRank({ topRankList, nowTabName }: TopRankProps) {
  return (
    <TopRankContainer>
      {topRankList.map((topRank) => (
        <TopRankContent
          key={topRank.rank}
          topRank={topRank}
          nowTapName={nowTabName}
        />
      ))}
    </TopRankContainer>
  );
}

export default TopRank;

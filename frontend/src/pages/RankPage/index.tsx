import { useState } from "react";
import { RankPageContainer } from "./styled";
import { RankType } from "@/interfaces/rank/RankType";
import TopRank from "./TopRank";
import RankTab from "./RankTab";
import RankTable from "./RankTable";
import RankPagination from "./RankPagination";

function Index() {
  const [topRankList, setTopRankList] = useState<RankType[]>();
  const [nowTabName, setNowTabName] = useState("asset");
  const [totalPage, setTotalPage] = useState(1);
  const [nowPage, setNowPage] = useState(0);
  return (
    <RankPageContainer>
      {topRankList && (
        <TopRank topRankList={topRankList} nowTabName={nowTabName} />
      )}
      <RankTab
        nowTabName={nowTabName}
        setNowTabName={setNowTabName}
        setNowPage={setNowPage}
      />
      <RankTable
        setTopRankList={setTopRankList}
        nowTabName={nowTabName}
        nowPage={nowPage}
        totalPage={totalPage}
        setTotalPage={setTotalPage}
      />
      <RankPagination
        nowPage={nowPage}
        setNowPage={setNowPage}
        totalPage={totalPage}
      />
    </RankPageContainer>
  );
}

export default Index;

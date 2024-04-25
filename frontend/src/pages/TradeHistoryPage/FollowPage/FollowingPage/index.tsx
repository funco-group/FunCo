import { useEffect, useState } from "react";
import { AxiosResponse } from "axios";
import { getCoinList, getTickerPrice } from "@/apis/upbit";
import FollowStatistics from "./FollowStatistics";
import { ResMarketCodeType } from "@/interfaces/PriceWindowType";
import { ResTickerType } from "@/interfaces/tradeHistory/follow/ResTickerType";
import FollowingUserListContainer from "./styled";
import FollowingUser from "./FollowingUser";
import { ComputedFollowingType } from "@/interfaces/tradeHistory/follow/ComputedFollowingType";
import { FollowingType } from "@/interfaces/tradeHistory/follow/FollowingTyps";
import { getFollowingList } from "@/apis/follow";
import { ResFollowingType } from "@/interfaces/tradeHistory/follow/ResFollowingType";
import SettleModal from "@/pages/TradeHistoryPage/FollowPage/FollowingPage/SettleModal";
import useSettleModalState from "@/hooks/recoilHooks/useSettleModalState";

function Index() {
  const [tickerList, setTickerList] = useState("");
  const [tickerPriceMap, setTickerPriceMap] = useState<Map<string, number>>(
    new Map(null),
  );
  const [isLoading, setIsLoading] = useState(false);
  const [totalAsset, setTotalAsset] = useState<number>();
  const [followings, setFollowings] = useState<FollowingType[]>();
  const [totalInvestment, setTotalInvesment] = useState<number>();
  const [totalEstimatedValue, setTotalEstimatedValue] = useState<number>();
  const [computedFollowings, setComputedFollowings] = useState<
    ComputedFollowingType[]
  >([]);
  const [investmentList, setInvestmentList] = useState<(string | number)[][]>(
    [],
  );

  const { settleModal } = useSettleModalState();

  // 코인 리스트 요청 및 설정
  const fetchCoinList = () => {
    getCoinList((res: AxiosResponse<ResMarketCodeType[]>) => {
      const newTickerList = res.data
        .filter((coin) => coin.market.startsWith("KRW"))
        .map((coin) => coin.market)
        .join(",");

      setTickerList(newTickerList);
    });
  };

  // 티커 가격 요청 및 맵 설정
  const fetchTickerPrice = () => {
    if (tickerList) {
      getTickerPrice(tickerList, (res: AxiosResponse<ResTickerType[]>) => {
        const mapList: [string, number][] = res.data.map((coin) => [
          coin.market,
          coin.trade_price,
        ]);
        setTickerPriceMap(new Map(mapList));
      });
    }
  };

  useEffect(() => {
    setIsLoading(true);
    fetchCoinList();
    getFollowingList((res: AxiosResponse<ResFollowingType>) => {
      const { data } = res;
      setTotalAsset(data.totalAsset);
      setFollowings(data.followings);
    });
  }, []);

  useEffect(() => {
    fetchTickerPrice();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [tickerList]);

  useEffect(() => {
    if (tickerPriceMap.size && totalAsset && followings !== undefined) {
      let newTotalInvestment = 0;
      let newTotalEstimatedValue = 0;
      const newComputedFollowings: ComputedFollowingType[] = [];
      const newInvestmentList: (string | number)[][] = [];
      followings.forEach((following) => {
        const computedFollowing = {
          followId: following.followId,
          nickname: following.nickname,
          date: following.followedAt,
          investment: following.investment,
          estimatedValue: Math.round(
            following.cash +
              following.coins.reduce((total, coin) => {
                const price = tickerPriceMap.get(coin.ticker) || 0;
                const value = coin.volume * price;
                return total + value;
              }, 0),
          ),
          cash: following.cash,
          coins: following.coins.map((coin) => {
            const tickerPrice = tickerPriceMap.get(coin.ticker) || 0;
            return {
              ticker: coin.ticker,
              price: tickerPrice * coin.volume,
            };
          }),
        };
        newComputedFollowings.push(computedFollowing);
        newInvestmentList.push([following.nickname, following.investment]);
        newTotalInvestment += following.investment;
        newTotalEstimatedValue += computedFollowing.estimatedValue;
      });
      setTotalInvesment(newTotalInvestment);
      setTotalEstimatedValue(newTotalEstimatedValue);
      setComputedFollowings(newComputedFollowings);
      setInvestmentList(newInvestmentList);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [totalAsset, followings, tickerPriceMap]);

  useEffect(() => {
    if (
      totalInvestment &&
      totalEstimatedValue &&
      investmentList &&
      computedFollowings
    ) {
      setIsLoading(false);
    }
  }, [
    totalInvestment,
    totalEstimatedValue,
    investmentList,
    computedFollowings,
  ]);

  if (isLoading) {
    return <></>;
  }
  return (
    <div>
      {settleModal && followings !== undefined && (
        <SettleModal followings={followings} setFollowings={setFollowings} />
      )}
      {totalInvestment && totalEstimatedValue && totalAsset && (
        <FollowStatistics
          totalAsset={totalAsset}
          totalInvestment={totalInvestment}
          totalEstimatedValue={totalEstimatedValue}
          investmentList={investmentList}
        />
      )}

      <FollowingUserListContainer>
        {computedFollowings.map((following) => (
          <FollowingUser key={following.followId} followingUser={following} />
        ))}
      </FollowingUserListContainer>
    </div>
  );
}

export default Index;

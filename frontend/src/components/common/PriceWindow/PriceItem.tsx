import { useNavigate, useParams } from "react-router-dom";
import { PriceType } from "@/interfaces/PriceWindowType";
import {
  PriceItemContainer,
  NameDiv,
  KorNameDiv,
  CodeDiv,
  PriceDiv,
  UpdateDiv,
  ChangeDiv,
  ChangeRateDiv,
  ChangePriceDiv,
  TradePriceDiv,
  UnitDiv,
  StarIconDiv,
  StartIconImg,
} from "./PriceItem.styled";

interface PriceItemProps {
  price: PriceType;
  isFav: boolean;
  onClickFavorite: (code: string, e: React.MouseEvent<HTMLElement>) => void;
}

function PriceItem({ price, isFav, onClickFavorite }: PriceItemProps) {
  const coloredStar =
    "https://cdn.upbit.com/upbit-web/images/icon_list_favorite.c3deb14.svg";
  const star =
    "https://cdn.upbit.com/upbit-web/images/icon_list_favorite_disabled.4bd898a.svg";
  const { coinCode } = useParams();
  const navigate = useNavigate();

  const clickCoin = (code: string) => {
    navigate(`/trade/${code}`);
  };

  return (
    <PriceItemContainer
      $selected={coinCode === price.code}
      onClick={() => clickCoin(price.code)}
    >
      <StarIconDiv>
        <StartIconImg
          onClick={(e) => onClickFavorite(price.code, e)}
          src={isFav ? coloredStar : star}
          alt="start-icon"
        />
      </StarIconDiv>
      <NameDiv>
        <KorNameDiv>
          <img
            src={`https://static.upbit.com/logos/${price.code.split("-")[1]}.png`}
            alt={price.code}
            width={13}
          />
          {price.koreanName}
        </KorNameDiv>
        <CodeDiv>{price.code}</CodeDiv>
      </NameDiv>
      <PriceDiv $isDown={price.change === "FALL"}>
        <UpdateDiv
          $updated={price.updated}
          $updatedDown={price.change === "FALL"}
        >
          {price.tradePrice.toLocaleString("ko-KR")}
        </UpdateDiv>
      </PriceDiv>
      <ChangeDiv $isDown={price.change === "FALL"}>
        <ChangeRateDiv>
          {price.change === "RISE" && "+"}
          {(price.signedChangeRate * 100).toFixed(2)}%
        </ChangeRateDiv>
        <ChangePriceDiv>
          {price.signedChangePrice.toLocaleString("ko-KR")}
        </ChangePriceDiv>
      </ChangeDiv>
      <TradePriceDiv>
        <div>
          {Math.round(price.accTradePrice24h / 1000000).toLocaleString("ko-KR")}
        </div>
        <UnitDiv>백만</UnitDiv>
      </TradePriceDiv>
    </PriceItemContainer>
  );
}

export default PriceItem;

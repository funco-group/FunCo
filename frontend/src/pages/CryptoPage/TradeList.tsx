import { useEffect, useState } from "react";
import { useRecoilValue } from "recoil";
import { AxiosResponse } from "axios";
import { useParams } from "react-router-dom";
import {
  ToggleContainer,
  CircleDiv,
  ToggleDiv,
  ToggleText,
  TradeListContainer,
  NoTradeData,
} from "./TradeList.styled";
import TradeListItem from "./TradeListItem";
import {
  ColumnContainer,
  ColumnGrid,
  ColumnTitleDiv,
} from "@/styles/CommonStyled";
import AlertModal from "@/components/common/Modal/AlertModal";
import userState from "@/recoils/user";
import { TradeListType } from "@/interfaces/TradeType";
import { cancleOrder, getOpenTradeList, getTradeList } from "@/apis/trade";

function TradeList() {
  const { coinCode } = useParams();
  const user = useRecoilValue(userState);
  const toggles = ["체결", "미체결"];
  const columns = ["주문시간", "구분", "주문가격", "주문량", "취소"];
  const concludeColumns = ["주문시간", "구분", "주문가격", "주문량"];
  const [selected, isSelected] = useState<string>("체결");
  const [tradeList, setTradeList] = useState<TradeListType[]>([]);
  const [alert, setAlert] = useState<boolean>(false);
  const [alertContent, setAlertContent] = useState<string>("");

  useEffect(() => {
    setTradeList([]);
    if (!user.user) {
      setAlert(true);
      setAlertContent("로그인이 필요합니다.");
    } else if (selected === "체결") {
      getTradeList(
        coinCode!,
        0,
        10,
        (response: AxiosResponse<TradeListType[]>) => {
          const { data } = response;
          setTradeList(data);
        },
      );
    } else {
      getOpenTradeList(
        coinCode!,
        0,
        10,
        (response: AxiosResponse<TradeListType[]>) => {
          const { data } = response;
          setTradeList(data);
        },
      );
    }
  }, [coinCode, selected]);

  const changeSelect = (toggle: string) => {
    isSelected(toggle);
  };

  const closeAlert = () => {
    setAlert(false);
  };

  const clickCancle = (id: number) => {
    // 거래를 취소하시겠습니까? 모달
    cancleOrder(id, () => {
      setAlertContent("주문이 취소되었습니다.");
      setAlert(true);
      setTradeList(tradeList.filter((prevTrade) => prevTrade.id !== id));
    });
  };

  return (
    <div>
      {alert && (
        <AlertModal
          title="알림"
          content={alertContent}
          closeAlert={closeAlert}
        />
      )}
      <ToggleContainer>
        {toggles.map((toggle: string) => {
          return (
            <ToggleDiv key={toggle}>
              <CircleDiv
                $active={selected === toggle}
                onClick={() => changeSelect(toggle)}
              />
              <ToggleText onClick={() => changeSelect(toggle)}>
                {toggle}
              </ToggleText>
            </ToggleDiv>
          );
        })}
      </ToggleContainer>
      <ColumnContainer>
        <ColumnGrid
          column={
            selected === "체결"
              ? "6rem 6rem 9rem 8rem"
              : "5.5rem 6rem 7.2rem 7.1rem 3.5rem"
          }
        >
          {selected === "체결" ? (
            <>
              {concludeColumns.map((column) => (
                <ColumnTitleDiv key={column}>{column}</ColumnTitleDiv>
              ))}
            </>
          ) : (
            <>
              {columns.map((column) => (
                <ColumnTitleDiv key={column}>{column}</ColumnTitleDiv>
              ))}
            </>
          )}
        </ColumnGrid>
      </ColumnContainer>
      <TradeListContainer>
        {!user.user && <NoTradeData>로그인 후 확인할 수 있습니다.</NoTradeData>}
        {user.user && !tradeList && (
          <NoTradeData>{selected} 내역이 없습니다.</NoTradeData>
        )}
        {user.user &&
          tradeList &&
          tradeList.map((trade) => (
            <TradeListItem
              key={trade.id}
              trade={trade}
              selected={selected}
              clickCancle={clickCancle}
            />
          ))}
      </TradeListContainer>
    </div>
  );
}

export default TradeList;

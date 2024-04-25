import { useEffect, useState } from "react";
import {
  OpenOrderContentTableContainer,
  OpenOrderContentListContainer,
} from "./OpenOrderContentTable.style";
import {
  ColumnContainer,
  ColumnGrid,
  ColumnTitleDiv,
} from "@/styles/CommonStyled";
import OpenOrderContent from "./OpenOrderContent";
import { TradeListType } from "@/interfaces/TradeType";
import { cancleOrder, getAllOpenTradeList } from "@/apis/trade";
import AlertModal from "@/components/common/Modal/AlertModal";

function OpenOrderContentTable() {
  const [openOrderContentList, setOpenOrderContentList] =
    useState<TradeListType[]>();
  const [isLoading, setIsLoading] = useState(false);
  const [alert, setAlert] = useState<boolean>(false);
  const [alertContent, setAlertContent] = useState<string>("");

  const openOrderColumnList = [
    "주문시간",
    "거래 종류",
    "가상화폐",
    "주문단가",
    "주문금액",
    "주문량",
    "취소",
  ];

  useEffect(() => {
    setIsLoading(true);
    getAllOpenTradeList(0, 100, (res) => {
      const { data } = res;
      setOpenOrderContentList(data);
    });
  }, []);

  useEffect(() => {
    if (openOrderContentList !== undefined) {
      setIsLoading(false);
    }
  }, [openOrderContentList]);

  const handleCancelOpenOrder = (id: number) => {
    if (openOrderContentList !== undefined) {
      cancleOrder(id, () => {
        setAlertContent("주문이 취소되었습니다.");
        setAlert(true);
        const newOpenOrderContentList = [...openOrderContentList].filter(
          (order) => order.id !== id,
        );
        setOpenOrderContentList(newOpenOrderContentList);
      });
    }
  };

  const closeAlert = () => {
    setAlert(false);
  };

  if (isLoading) {
    return <></>;
  }
  return (
    <OpenOrderContentTableContainer>
      {alert && (
        <AlertModal
          title="알림"
          content={alertContent}
          closeAlert={closeAlert}
        />
      )}
      <ColumnContainer>
        <ColumnGrid column="7rem 7rem 8rem 1fr 1fr 1fr 9rem">
          {openOrderColumnList.map((column) => (
            <ColumnTitleDiv key={column}>{column}</ColumnTitleDiv>
          ))}
        </ColumnGrid>
      </ColumnContainer>
      <OpenOrderContentListContainer>
        {openOrderContentList?.map((content) => (
          <OpenOrderContent
            key={content.id}
            content={content}
            handleCancelOpenOrder={handleCancelOpenOrder}
          />
        ))}
      </OpenOrderContentListContainer>
    </OpenOrderContentTableContainer>
  );
}

export default OpenOrderContentTable;

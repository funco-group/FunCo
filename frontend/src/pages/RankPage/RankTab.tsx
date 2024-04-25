import { Dispatch, SetStateAction } from "react";
import TabButton from "@/components/common/Button/TabButton.styled";
import { RankTabContainer, TimeDiv } from "./RankTab.styled";

interface RankTabProps {
  nowTabName: string;
  setNowTabName: Dispatch<SetStateAction<string>>;
  setNowPage: Dispatch<SetStateAction<number>>;
}

const buttonHeight = "2.1875rem";

function RankTab({ nowTabName, setNowTabName, setNowPage }: RankTabProps) {
  const tabNameList: [string, "asset" | "follow", "right" | "left"][] = [
    ["총 자산", "asset", "left"],
    ["총 팔로워 금액", "follow", "right"],
  ];

  const handleTabClick = (selectedTabName: "asset" | "follow") => {
    setNowTabName(selectedTabName);
    setNowPage(0);
  };

  const nowDate = new Date();
  const year = nowDate.getFullYear();
  const month = String(nowDate.getMonth() + 1).padStart(2, "0");
  const day = String(nowDate.getDate()).padStart(2, "0");
  const hour = String(nowDate.getHours()).padStart(2, "0");
  // const minutes = nowDate.getMinutes() >= 30 ? "30" : "00";
  const minutes = String(nowDate.getMinutes()).padStart(2, "0");
  const rankCalDate = `${year}-${month}-${day} ${hour}:${minutes} 기준`;

  return (
    <RankTabContainer>
      <div>
        {tabNameList.map((tabName, idx) => (
          <TabButton
            key={tabName[1]}
            width={idx === 0 ? "4.5rem" : null}
            height={buttonHeight}
            $active={nowTabName === tabName[1]}
            onClick={() => handleTabClick(tabName[1])}
            radius={tabName[2]}
          >
            {tabName[0]}
          </TabButton>
        ))}
      </div>
      <TimeDiv>·{rankCalDate}</TimeDiv>
    </RankTabContainer>
  );
}

export default RankTab;

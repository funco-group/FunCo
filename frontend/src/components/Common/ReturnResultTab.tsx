import { OptionType } from "@/interfaces/AssetType";
import TabButton from "./Button/TabButton.styled";
import {
  ReturnResultTabContainer,
  DateDiv,
  DateSetDiv,
} from "./ReturnResultTab.styled";

interface ReturnResultTabProps {
  activeTab: string;
  handleTabClick: (tab: string) => void;
  options: OptionType[];
  selected: string | undefined;
  handleSelect: (e: any) => void;
}

function ReturnResultTab({
  activeTab,
  handleTabClick,
  options,
  selected,
  handleSelect,
}: ReturnResultTabProps) {
  const tabs = ["일별", "월별"];

  return (
    <ReturnResultTabContainer>
      <DateDiv>
        {selected
          ? selected?.includes("-")
            ? `${selected.split("-")[0]}년 ${selected.split("-")[1]}월`
            : `${selected}년`
          : ""}
        {} 투자 손익
      </DateDiv>
      <DateSetDiv>
        {tabs.map((tab) => {
          return (
            <TabButton
              key={tab}
              width="4rem"
              height="2.5rem"
              $active={tab === activeTab}
              onClick={() => handleTabClick(tab)}
              radius={tab === "일별" ? "left" : "right"}
            >
              {tab}
            </TabButton>
          );
        })}
        <select onChange={handleSelect} value={selected}>
          {options.map((option: OptionType) => {
            return (
              <option value={option.value} key={option.value}>
                {option.name}
              </option>
            );
          })}
        </select>
      </DateSetDiv>
    </ReturnResultTabContainer>
  );
}

export default ReturnResultTab;

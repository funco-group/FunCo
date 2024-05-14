import { TabContainer, TabItemDiv } from "./Tab.style";

interface Props {
  columns: number;
  tabs: string[];
  activeTab: string;
  changeTab: (tab: string) => void;
}

function Tab({ columns, tabs, activeTab, changeTab }: Props) {
  return (
    <TabContainer columns={columns}>
      {tabs.map((tab) => (
        <TabItemDiv
          key={tab}
          $active={activeTab === tab}
          onClick={() => changeTab(tab)}
        >
          {tab}
        </TabItemDiv>
      ))}
    </TabContainer>
  );
}

export default Tab;

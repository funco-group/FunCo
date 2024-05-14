import { OptionType } from '@/interfaces/AssetType'
import TabButton from './Button/TabButton.styled'
import {
  ReturnResultTabContainer,
  DateDiv,
  DateSetDiv,
} from './ReturnResultTab.styled'

interface ReturnResultTabProps {
  activeTab: string
  handleTabClick: (tab: string) => void
  options: OptionType[]
  selected: string | undefined
  handleSelect: (e: any) => void
}

function ReturnResultTab({
  activeTab,
  handleTabClick,
  options,
  selected,
  handleSelect,
}: ReturnResultTabProps) {
  const tabs = ['일별', '월별']

  const renderDateInfo = (selectedDate: string | undefined): string => {
    if (!selectedDate) return ''
    const parts = selectedDate.split('-')
    if (parts.length > 1) {
      return `${parts[0]}년 ${parts[1]}월 투자 손익`
    }
    return `${selectedDate}년 투자 손익`
  }

  return (
    <ReturnResultTabContainer>
      <DateDiv>{renderDateInfo(selected)}</DateDiv>
      <DateSetDiv>
        {tabs.map((tab) => (
          <TabButton
            key={tab}
            width="4rem"
            height="2.5rem"
            $active={tab === activeTab}
            onClick={() => handleTabClick(tab)}
            radius={tab === '일별' ? 'left' : 'right'}
          >
            {tab}
          </TabButton>
        ))}
        <select onChange={handleSelect} value={selected}>
          {options.map((option: OptionType) => (
            <option value={option.value} key={option.value}>
              {option.name}
            </option>
          ))}
        </select>
      </DateSetDiv>
    </ReturnResultTabContainer>
  )
}

export default ReturnResultTab

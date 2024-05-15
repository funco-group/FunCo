import {
  AmountDiv,
  ButtonContainer,
  ContentDiv,
  PriceButton,
  PriceButtons,
  PriceInput,
  TitleDiv,
  TradeInnerContainer,
  TradeItem,
} from '@/styles/Trade.styled'
import React, { useState } from 'react'
import { FuturesTradeButton } from './FuturesTrade.styled'
import { getCash } from '@/apis/member'
import { AxiosResponse } from 'axios'
import { CashType } from '@/interfaces/common/AssetType'

function FuturesTrade() {
  const volumeButtons = [10, 20, 25, 30, 40, 50, 75, 100]
  const [buttons, setButtons] = useState<string[]>(['Long', 'Short'])
  const [cash, setCash] = useState<number>(0) // 주문 가능
  const [volume, setVolume] = useState<number>(50)

  getCash((response: AxiosResponse<CashType>) => {
    const { data } = response
    setCash(data.cash)
  })

  return (
    <TradeInnerContainer>
      <div>
        <TradeItem>
          <TitleDiv>주문 가능</TitleDiv>
          <ContentDiv>
            {cash.toLocaleString('ko-KR')}
            <div>WON</div>
          </ContentDiv>
        </TradeItem>
        <TradeItem>
          <TitleDiv>레버리지</TitleDiv>
          <ContentDiv>
            <input
              type="range"
              onChange={(e) => {
                setVolume(e.target.valueAsNumber)
              }}
            />
            <div>{volume} X</div>
          </ContentDiv>
        </TradeItem>
        <TradeItem>
          <TitleDiv>주문총액</TitleDiv>
          <AmountDiv>
            <PriceInput
              // value={formattedVolume}
              placeholder="0"
              // onChange={(e) => {
              //   inputDecimalFormat(
              //     e,
              //     setFormattedVolume,
              //     setOrderVolume,
              //     '매도',
              //   )
              // }}
            />
          </AmountDiv>
        </TradeItem>
        <TradeItem>
          <div />
          <PriceButtons>
            {volumeButtons.map((rate) => {
              return (
                <PriceButton
                  key={rate}
                  // onClick={() => clickVolumeButton(rate)}
                >
                  {rate}%
                </PriceButton>
              )
            })}
          </PriceButtons>
        </TradeItem>
      </div>
      <ButtonContainer>
        {buttons.map((button) => {
          return <FuturesTradeButton name={button}>{button}</FuturesTradeButton>
        })}
      </ButtonContainer>
    </TradeInnerContainer>
  )
}

export default FuturesTrade

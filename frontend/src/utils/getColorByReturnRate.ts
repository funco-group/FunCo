const getColorByReturnRate = (returnRate: number): string => {
  if (returnRate > 0) return 'red'
  if (returnRate < 0) return 'blue'
  return 'black'
}

export default getColorByReturnRate

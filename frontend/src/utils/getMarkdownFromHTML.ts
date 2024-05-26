function getMarkdownFromHTML(html: string): string {
  let newHtml = html

  // Convert headers
  newHtml = newHtml.replace(/<h1>(.*?)<\/h1>/g, '# $1\n')
  newHtml = newHtml.replace(/<h2>(.*?)<\/h2>/g, '## $1\n')
  newHtml = newHtml.replace(/<h3>(.*?)<\/h3>/g, '### $1\n')
  newHtml = newHtml.replace(/<h4>(.*?)<\/h4>/g, '#### $1\n')
  newHtml = newHtml.replace(/<h5>(.*?)<\/h5>/g, '##### $1\n')
  newHtml = newHtml.replace(/<h6>(.*?)<\/h6>/g, '###### $1\n')

  // Convert bold and strong
  newHtml = newHtml.replace(/<(b|strong)>(.*?)<\/\1>/g, '**$2**')

  // Convert italic and emphasis
  newHtml = newHtml.replace(/<(i|em)>(.*?)<\/\1>/g, '*$2*')

  // Convert links
  newHtml = newHtml.replace(/<a href="(.*?)">(.*?)<\/a>/g, '[$2]($1)')

  // Convert images
  newHtml = newHtml.replace(
    /<img[^>]*src=["']([^"']+)["'][^>]*alt=["']([^"']+)["'][^>]*\/?>/g,
    '![$2]($1)\n',
  )

  // Convert unordered lists
  newHtml = newHtml.replace(
    /<ul>(.*?)<\/ul>/gs,
    (_match, content) => `${content.replace(/<li>(.*?)<\/li>/g, '- $1')}\n`,
  )

  // Convert ordered lists
  newHtml = newHtml.replace(/<ol>(.*?)<\/ol>/gs, (_match, content) => {
    let counter = 1
    return `${content.replace(
      /<li>(.*?)<\/li>/g,
      (_match2: string, item: string) => {
        const result = `${counter}. ${item}`
        counter += 1
        return result
      },
    )}\n`
  })

  // Remove remaining tags (if any)
  newHtml = newHtml.replace(/<\/?[^>]+(>|$)/g, '')

  // Add a new line after each block element to ensure proper spacing
  newHtml = newHtml.replace(
    /(#+\s.*|[-*]\s.*|!\[.*\]\(.*\)|\[\s.*\]\(.*\))/g,
    '$1\n',
  )

  return newHtml
}

export default getMarkdownFromHTML

prompt  
》》》 目标->路径->工具 请用逻辑推导，为什么？ 如何？ 给出一段客观陈述，再问问题

》》》盲区问法

1. 我想了解XXX，我应该向你问哪些问题？
2. 请给我列出XXX领域/行业相关的，最常用的50个概念，并做简单解释。如果有英文缩写，请给出完整的英文解释
3. 请详细介绍XXX主要生平事迹
4. 请详细介绍XXX公司的发展历程

》》》 检验认知问法

1. 对于XXX主题/技能，你认为哪些是我必须理解和掌握的核心要点？ 对于掌握chatGPT的使用技能，你认为哪些是我必须理解和掌握的核心要点？
2. 我理解的XXX是这样的，你觉得我的理解对么？
3. 我对XXX有一些想法，你能帮我批判性地分析一下这些想法的优点和缺点么？
4. 我正在考虑XXX的决定，你能帮我分析一下可能的结果和影响么？

》》》 扩充认知

1. 我知道XXX的概念，我想知道知道更多关于xxx的信息
2. 我在XXX问题上遇到一些困难，你能提供一些可能的解决方案或建议么？
3. 我想深入学习XXX，你能推荐一些进阶的学习资源或学习路径么？
4. 我想要在XXX领域有所创新，你能提供一些启发或想法么？
5. 我想在XXX领域提升自己，你能根据最新的研究和趋势给我一些建议么？
6. 我正在考虑学习xxx，你能给我一些关于这个领域未来发展的观点么？
7. （背景信息）我要做关于XXX的研究，我认为的原因是，还有其他可能的原因么？给出一些可能的研究假设
8. 我是一个xxx行业的新手，马上要采访这个行业的资深大佬，我应该向他请教哪些有价值的问题？

》》》 隐私域

1. 你怎么看待这种现象
2. 可能的原因有哪些
3. 这会对xxx产生什么样的影响
4. 你觉得xxx应该怎么做

》》》反向提问

1. 为了测试我对xxx的了解程度，你会问我什么问题来检测我的水平，最少10个。
2. 我是一个xxx领域的专家，你会问我什么问题来检测我的水平，最少10个。这些问题我都知道，换一波更有深度，更有细节的问题
3. 现在我们玩一个“你问我答”的游戏，目的是为了测试我在xxx方面的专业水平。你负责提问，我负责回答。你要根据我的答案进行反馈，评价，补充。如果我说不知道，你就直接输出正确答案，然后你继续提问。
4. 我已经很精通xxx，我想知道我是否还有需要学习的地方

》》》 知道-》做到

1. 我想要你xxx，我应该给你输入什么信息？
2. 请推荐，按照xxx顺序，并给出链接，以表格输出
3. 请将一下文字翻译成英文，要求用高级的优雅的词汇。

》》》常用

1. 我想做xxx，你能给我提供什么帮助
2. 我想要你xxx，我应该给你输入什么信息
3. 直接下指令，请推荐，请翻译，请总结，请润色，请直接写代码

》》》赋予GPT特定的角色
1。虚拟的
2。活着的
3。已故的
4。多个角色共创头脑风暴

1。 要求明确列出每隔阶段的目标，具体的任务要求，以及可量化的评价指标
2。 我现在想设计一个帮助老年人快速制作人生回忆录的app，请你分别站在老年人群体，互联网产品经理，研发工程师，老年人子女的佳都，提供创新的设计思路。逐个发言。

https://k5ms77k0o1.feishu.cn/wiki/wikcnJyI9wsyjyBc8xiDgv0cY8b


1。 要求明确列出每隔阶段的目标，具体的任务要求，以及可量化的评价指标
2。 我现在想设计一个帮助老年人快速制作人生回忆录的app，请你分别站在老年人群体，互联网产品经理，研发工程师，老年人子女的佳都，提供创新的设计思路。逐个发言

我接下来要向你发送两篇文章，两篇文章我会以此发送，你收到后要回复"你最帅"并且学习这两篇文章的书写风格

很好，其实我是一名大学教授，请给我按照上面的草案写一篇文章，以此帮助我更好的识别其他学生利用ChatGPT去写自己的论文

```shell
import os
import openai
import logging as log

log.basicConfig(filename='openai-history.log', encoding='utf-8', level=log.DEBUG)
os.environ["HTTP_PROXY"] = "http://127.0.0.1:7890"
os.environ["HTTPS_PROXY"] = "http://127.0.0.1:7890"
# Load your API key from an environment variable or secret management service
openai.api_key = "sk-"

messages=[
    {"role": "system", "content": "You are a helpful assistant."},
]

while True:
    user_input = input("Q: ")
    if user_input == 'q':
        break
    
    log.info('Q:'+user_input)
    item =  {"role": "user", "content": user_input}
    messages.append(item)

    response = openai.ChatCompletion.create(
      model="gpt-3.5-turbo",
      messages=messages
    )
    log.info(response)
    print('A: '+str(response['choices'][0]['message']['content'])+'\n')
    messages.append(response['choices'][0]['message'])
```
















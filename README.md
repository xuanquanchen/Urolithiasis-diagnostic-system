# 泌尿结石诊断系统 Urolithiasis-diagnostic-system

The form-based urolithiasis diagnosis system includes a complete front end and back end

## 1. 泌尿结石诊断辅助系统简介

### 1.1. 概述

泌尿结石诊断辅助系统的主要任务之一是基于深度学习训练得出的神经网络模型，对患者现有的病理特征进行分析，从而初步诊断出患者的结石类型，为诊断者提供有利参考，辅助诊断者更快、更好地确定发病者的病理状态，解决肾结石类型早期诊断工作复杂、繁琐的问题。在辅助诊断的同时，系统对诊断结果进行记录，为诊断者了解近期患者结石类型变化趋势提供一个参考；并通过与实际结果进行对比，以指导诊断模型后期的修正工作。

本系统采用前台（客户端）和后台（管理/诊断端）分离的方式，将该系统对于不同角色的业务入口分隔为两部分。关于客户端及管理/诊断端使用者的定义如下：
客户端使用者：包括但不限于使用该系统进行诊断的患者、普通用户以及该系统诊断功能的体验者、测试者。
管理/诊断端使用者：使用该系统进行辅助诊断及结石类型趋势分析的医生、诊断者、专家，使用该系统进行模型分析的专家、开发者以及对用户信息进行调整的管理员等。

⚠注意：在使用该辅助系统的过程中，您需要特别注意的是：由于现阶段技术的局限性，本系统无法替代专业诊疗人员，并不能保证诊断准确率为100%。如您是管理/诊断端使用者，系统只能给予诊断过程一个有利参考；如您是客户端使用者，在使用该系统诊断的过程中，务必在医生、专家等人员的指导下进行。本系统对于错误使用该系统的行为造成的损失不负责任！



## 2. 泌尿结石诊断辅助系统环境需求

### 2.1. 系统服务器环境需求

#### 2.1.1. 硬件要求

◎硬盘空间：数据空间5GB，数据库存储空间由用户需求决定

◎内存：不小于2G

#### 2.1.2. 软件要求

◎操作系统：CentOS 8.0+

◎运行环境：Java V1.8+

◎数据库软件：Mysql V8.0.21+

◎静态资源托管及反向代理环境：Nginx V1.21.0+

### 2.2. 系统客户端环境需求

#### 2.2.1. 硬件要求

◎客户机：intel core i5或更高性能的微机

◎内存：不小于1GB

#### 2.2.2. 软件要求

◎Microsoft IE 11或更高版本的Web浏览器



## 3.泌尿结石诊断辅助系统使用指南

本章将依次对泌尿结石诊断辅助系统的用户端及管理/诊断端的功能及其使用方法进行指导。

### 3.1. 用户端使用指南

泌尿结石诊断辅助系统用户端包括用户登录、发起诊断、获取医生建议、查看历史记录、获取账户信息功能。

泌尿结石诊断辅助系统用户端可以有以下使用方式：

用户登录：用户在使用本系统用户端时，可以选择使用已授权的账号-密码登录或者以“游客”身份登录。使用该系统用户端时，用户至少选择其中一种登录方式以进行发起诊断、获取医生建议、查看历史记录、获取账户信息等操作。用户在完成工作后，可以选择退出登录以完成当前会话注销。如想再次使用该系统客户端，必须再次登录。

发起诊断：用户根据实际情况填写个人基本信息（如姓名、性别等）、日常症状（如发烧、恶心等）、日常习惯（如有无吸烟、饮酒等）以及其他与疾病相关的基本信息（如CR指标、BUN指标等），完成填写后手动提交信息，系统将给予用户查看诊断结果。

获取医生建议：用户通过系统查看医生/专家等相关人员给予用户预防或治疗泌尿结石的建议或指导等。

查看历史记录：用户诊断完成后，根据基本诊断结果、日期等信息回顾详细的诊断结果。

获取用户信息：用户获取个人信息，并可进行信息更正、会话注销操作。

#### 3.1.1. 用户登录

用户进行账户-密码登录或者游客登录，以获取用户端的进入权限。

用户在进行账户-密码登录的操作时，必须填写以下信息：

◎用户名：必填项，默认为空；

◎密码：必填项，默认为空。

用户在进行游客登录的操作时，不需要填写用户名或密码。

当用户拥有已授权的用户名或密码时，可以使用账户-密码登录方式登入用户端，此时应当点击“登录”按钮以进入用户端；当用户没有已授权的用户名或密码时，可直接点击“游客登录”按钮并以“游客”身份进入用户端。

⚠注意：在用户端的使用过程中，“游客”并不完全拥有已授权账户的所有权限，包括但不限于历史记录的云端备份、个人信息的更改、个性化医生建议等。

#### 3.1.2. 发起诊断

用户根据实际情况填写个人基本信息（如姓名、性别等）、日常症状（如发烧、恶心等）、日常习惯（如有无吸烟、饮酒等）以及其他与疾病相关的基本信息（如CR指标、BUN指标等），完成填写后手动提交信息，系统将给予用户查看诊断结果。

用户在进行发起诊断的操作时，应当填写以下信息：

◎姓名：必填项，默认为空，长度为1～15个字符；

◎性别：必填项，默认为“女”；

◎年龄：必填项，默认为“1”，数值在1～120之间；

◎婚姻状况：必填项，默认为“未婚”；

◎出生地：必填项，默认为“Sowme'eh sara”；

◎日常症状：必填项，默认为“腰痛”；

◎发烧症状：必填项，默认为“是”；

◎恶心症状：必填项，默认为“是”；

◎尿中带血：必填项，默认为“是”；

◎糖尿病：必填项，默认为“是”；

◎住院史：必填项，默认为“是”；

◎血凝块：必填项，默认为“是”；

◎肌酐水平（Cr）：必填项，默认为“低”；

◎血红蛋白水平（HB）：必填项，默认为“低”；

◎尿素水平（BUN）: 必填项，默认为“低”；

◎是否有吸烟的习惯：必填项，默认为“是”；

◎是否有饮酒的习惯：必填项，默认为“是”；

◎是否有吸毒的习惯：必填项，默认为“是”；

◎血压水平（BP）：必填项，默认为“低”；

◎家人是否患有类似的疾病：必填项，默认为“是”；

◎尿酸水平：必填项，默认为“低”；

◎血钙水平：必填项，默认为“低”；

◎疾病诊断：必填项，默认为“肾髓质结石”。

用户完成上述信息的填写后，必须手动点击“开始预测”按钮以提交信息。用户应当如实填写相关信息，以提高预测的准确性和灵敏度。

在返回结果中：“MultilayerPerception”、“SMO”等列表将代表对应模型的预测结果，列表下为对应模型预测的各结石类型概率。

#### 3.1.3. 获取医生建议

用户通过系统查看医生/专家等相关人员给予用户预防或治疗泌尿结石的建议或指导等。

用户通过点击用户端主界面“建议”按钮，即可打开“医生建议”列表，在点击任意一条建议后，系统将打开对应建议的显示窗口，点击右上角“×”即可关闭该窗口。

#### 3.1.4. 查看历史记录

用户诊断完成后，根据基本诊断结果、日期等信息回顾详细的诊断结果。

用户通过点击用户端主界面“历史”按钮，即可打开“历史记录”列表。在点击任意一条历史记录后，系统将进入对应记录的详细诊断结果页面。点击右上角按钮或底部“返回重试”按钮即可返回用户端主界面。

历史记录的详细诊断结果页面同4.1.2中诊断结果页面相一致。

#### 3.1.5. 获取账户信息

用户获取个人信息，并可进行信息更正、会话注销操作。

用户通过点击用户端主界面“我的”按钮，即可打开“账户信息”窗口，并显示用户头像、用户名。点击“修改信息”按钮即可进行账户信息的修改；点击退出登录即可完成当前会话注销。

⚠注意：“游客”无法进行账户信息的修改。


### 3.2. 管理/诊断端使用指南

泌尿结石诊断辅助系统管理/诊断端包括用户登录、单次诊断、多次诊断、查看历史诊断、查看所有用户诊断记录、查看近期结石类型趋势变化、查看使用帮助、查看通知、更改账户信息、分享应用、发起反馈、查看版本信息功能。

用户登录：用户在使用本系统管理/诊断端时，必须使用已授权的账号-密码登录。使用该系统管理/诊断端时，用户必须申请成为本系统的合法用户，以进行单次诊断、多次诊断、查看历史诊断、查看所有用户诊断记录、查看近期结石类型趋势变化、查看使用帮助、查看通知、更改账户信息、分享应用、发起反馈、查看版本信息等操作。用户在完成工作后，可以选择退出登录以完成当前会话注销。如想再次使用该系统管理/诊断端，必须再次登录。

单次诊断：用户发起单次诊断，根据患者的实际情况填写个人基本信息（如姓名、性别等）、日常症状（如发烧、恶心等）、日常习惯（如有无吸烟、饮酒等）以及其他与疾病相关的基本信息（如CR指标、BUN指标等），完成填写后手动提交信息，系统将给予用户查看诊断结果。

多次诊断：用户同时发起多次诊断，依次填写多位患者的相关信息，在手动提交后系统将返回多次诊断的所有诊断结果。

查看历史诊断：用户根据患者姓名或诊断提交时间回顾诊断者曾经诊断的所有记录。

查看所有用户诊断记录：管理/诊断端用户根据患者姓名或诊断提交时间回顾用户端所有用户曾经诊断的历史记录。

查看近期结石类型趋势变化：用户查看今日（较昨日）各类型结石预测增量、所有结石类型预测总量（比例）以及近七日各类型结石的预测变化。

查看使用帮助：用户查看关于管理/诊断端的使用帮助。

查看通知：用户查看关于管理/诊断端的各类通知。

更改账户信息：用户修改个人账户信息。

分享应用：用户分享泌尿结石诊断辅助系统的有效渠道。

发起反馈：用户在使用泌尿结石诊断辅助系统过程中对遇到的问题以及改善建议等进行反馈。

查看版本信息：用户查看当前管理/诊断端的版本信息。

#### 3.2.1. 用户登录

用户进行账户-密码登录，以获取管理/诊断端的进入权限。

用户在进行账户-密码登录的操作时，必须填写以下信息：

◎用户名：必填项，默认为空；

◎密码：必填项，默认为空。

#### 3.2.2. 单次诊断

用户发起单次诊断，根据患者的实际情况填写个人基本信息（如姓名、性别等）、日常症状（如发烧、恶心等）、日常习惯（如有无吸烟、饮酒等）以及其他与疾病相关的基本信息（如CR指标、BUN指标等），完成填写后手动提交信息，系统将给予用户查看诊断结果。

用户在进行单次诊断的操作时，应当填写以下信息：

◎姓名：必填项，默认为空，长度为1～15个字符；

◎性别：必填项，默认为“女”；

◎年龄：必填项，默认为“1”，数值在1～120之间；

◎婚姻状况：必填项，默认为“未婚”；

◎出生地：必填项，默认为“Sowme'eh sara”；

◎日常症状：必填项，默认为“腰痛”；

◎发烧症状：必填项，默认为“是”；

◎恶心症状：必填项，默认为“是”；

◎尿中带血：必填项，默认为“是”；

◎糖尿病：必填项，默认为“是”；

◎住院史：必填项，默认为“是”；

◎血凝块：必填项，默认为“是”；

◎肌酐水平（Cr）：必填项，默认为“低”；

◎血红蛋白水平（HB）：必填项，默认为“低”；

◎尿素水平（BUN）: 必填项，默认为“低”；

◎是否有吸烟的习惯：必填项，默认为“是”；

◎是否有饮酒的习惯：必填项，默认为“是”；

◎是否有吸毒的习惯：必填项，默认为“是”；

◎血压水平（BP）：必填项，默认为“低”；

◎家人是否患有类似的疾病：必填项，默认为“是”；

◎尿酸水平：必填项，默认为“低”；

◎血钙水平：必填项，默认为“低”；

◎疾病诊断：必填项，默认为“肾髓质结石”。

用户完成上述信息的填写后，必须手动点击“开始预测”按钮以提交信息。用户应当如实填写相关信息，以提高预测的准确性和灵敏度。

用户提交信息后，若填写信息无误并且系统完成预测将进入“历史诊断”页面以显示诊断结果。

#### 3.2.3. 多次诊断

用户同时发起多次诊断，依次填写多位患者的相关信息，在手动提交后系统将返回多次诊断的所有诊断结果。

用户在进行多次诊断的操作时，应当根据各患者实际情况填写同4.2.2相一致的相关信息；诊断者可通过表单右上角“＋”、“-”按钮分别进行诊断表单的增加、减少，通过页面底部的诊断页面栏可切换当前诊断表单。

用户提交信息后，若填写信息无误并且系统完成预测将进入“历史诊断”页面以显示诊断结果。

### 4.2.4. 查看历史诊断

用户根据患者姓名或诊断提交时间回顾诊断者曾经诊断的所有记录。

用户在进行查看历史诊断的操作时，应当填写以下信息：

◎姓名：选填项，默认为空，长度为1～15个字符；

◎日期：选填项，默认为空。

对于历史诊断记录的查询，用户可以选择以下几种查询方式：姓名、日期为空（返回所有记录）；姓名非空，日期为空（返回对应患者姓名的诊断记录）；姓名为空，日期为空（返回对应日期区间的诊断记录）；姓名、日期非空（返回对应患者姓名在对应日期区间的诊断记录）。日期的输入栏默认不展开，用户可以点击搜索框左侧按钮展开日期输入栏。

#### 3.2.5. 查看所有用户诊断记录

管理/诊断端用户根据患者姓名或诊断提交时间回顾用户端所有用户曾经诊断的历史记录。

管理/诊断端用户在进行查看用户端所有用户诊断记录的操作时，应当填写以下信息:

◎姓名：选填项，默认为空，长度为1～15个字符；

◎日期：选填项，默认为空。

对于用户端所有用户诊断记录的查询，与4.2.4中对于历史诊断记录的查询方式相一致。

#### 3.2.6. 查看近期结石类型趋势变化

用户查看今日（较昨日）各类型结石预测增量、所有结石类型预测总量（比例）以及近七日各类型结石的预测变化。

用户通过点击管理/诊断端主界面左侧导航栏的第三个按钮，即可查看近期结石类型趋势变化。点击结石类型预测统计卡片的右侧“下载”按钮，即可保存所有结石类型预测总量（比例）图；点击近一周结石类型预测变化卡片的右侧“下载”按钮，即可保存近七日各类型结石的预测变化图。

#### 3.2.7. 查看使用帮助

用户查看关于管理/诊断端的使用帮助。

用户通过点击管理/诊断端主界面右上方的第一个按钮，即可查看关于管理/诊断端的使用帮助。

#### 3.2.8. 查看通知

用户查看关于管理/诊断端的各类通知。

用户通过点击管理/诊断端主界面右上方的第二个按钮，即可查看关于管理/诊断端的各类通知。用户在点击任意一个通知后，即可打开一个详细显示对应通知的窗口。用户可以点击窗口右上角的“×”以关闭该窗口。

#### 3.2.9. 更改账户信息

用户修改个人账户信息。

用户通过点击管理/诊断端主界面右上方的第三个按钮，在展开列表中点击“更改账户信息”，即可进行更改账户信息操作。

用户在进行更改账户信息操作时，应填入以下信息：

◎用户名：必填项，默认为空，长度在3到10个字符之间；

◎密码：选填项，默认为空；

◎确认密码：选填项，默认为空，与“密码”项一致。

用户在进行更改账户信息的操作时，可以选择只修改用户名（此时密码与确认密码项应为空），也可以选择修改用户名和密码（此时密码与确认密码项应相一致）。

#### 3.2.10. 分享应用

用户分享泌尿结石诊断辅助系统的有效渠道。

用户通过点击管理/诊断端主界面右上方的第三个按钮，在展开列表中点击“分享应用”，即可进行分享应用操作。

#### 3.2.11. 发起反馈

用户在使用泌尿结石诊断辅助系统过程中对遇到的问题以及改善建议等进行反馈。

用户通过点击管理/诊断端主界面右上方的第三个按钮，在展开列表中点击“向我反馈”，即可进行发起反馈操作。

用户在进行发起反馈的操作时，应当填入以下信息：

◎反馈信息：必填项，默认为空。

#### 3.2.12. 查看版本信息

用户查看当前管理/诊断端的版本信息。

用户通过点击管理/诊断端主界面右上方的第三个按钮，在展开列表中点击“版本信息”，即可查看当前管理/诊断端的版本信息。


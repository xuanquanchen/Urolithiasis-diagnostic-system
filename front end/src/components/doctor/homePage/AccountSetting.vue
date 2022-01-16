<!--
  * @FileDescription:医生端账户设置
  * @Date:2021/10/4
  * @LastEditTime:2021/10/5
 -->
<template>
  <el-dialog
  :visible.sync="visible"
  title="更改账户信息"
  width="350px"
  :before-close="close"
  :close-on-click-modal="false">
    <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm">
      <el-form-item label="用户名" prop="name">
        <el-input v-model="ruleForm.name"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="pass">
        <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="checkPass">
        <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
        <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script>
export default {
  props: {
    visible: {
      default: false,
      type: Boolean
    }
  },
  data () {
    return {
      ruleForm: {
        pass: '',
        checkPass: '',
        name: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.ruleForm.pass !== '' && this.ruleForm.checkPass === '') {
            this.$message.error('请再次输入密码！')
          } else if (this.ruleForm.pass !== '' && this.ruleForm.checkPass !== this.ruleForm.pass) {
            this.$message.error('两次密码输入不一致！')
          } else {
            this.submit()
          }
        } else {
          this.$message.error('提交失败！')
          return false
        }
      })
    },
    resetForm (formName) {
      this.$refs[formName].resetFields()
    },
    submit () {
      this.$message.error('当前暂不开放修改密码业务，请联系管理员进行操作！')
      this.$emit('close')
    },
    close () {
      this.$emit('close')
    }
  }
}
</script>
